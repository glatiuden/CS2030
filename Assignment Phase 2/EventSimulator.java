package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The EventSimulator class encapsulates all the events. There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop (the
 * servers) and (iv) random generator.
 */
public class EventSimulator {
    /** The priority queue of events. */
    private final PriorityQueue<Event> events;
    /** The statistics maintained. */
    private final Statistics stats;
    /** The shop of servers. */
    private final Shop shop;
    /**
     * The random number generator used to simulate of different arrival and service
     * times.
     */
    private final RandomGenerator gen;

    /**
     * A public constructor of internal states.
     * 
     * @param seed              Denoting the base seed for the RandomGenerator
     *                          object.
     * @param noOfServers       Representing the number of servers.
     * @param noOfSelfCheckouts Representing the number of self-checkout counters.
     * @param length            The maximum queue length.
     * @param noOfCustomers     Representing the number of customers.
     * @param arrRate           The arrival rate of customers.
     * @param svcRate           The service rate of servicers.
     * @param restRate          The resting rate of servers.
     * @param restProb          The probability of resting.
     * @param greedyProb        The probability of a greedy customer occurring.
     */
    public EventSimulator(int seed, int noOfServers, int noOfSelfCheckouts, int length, 
            int noOfCustomers, double arrRate, double svcRate, double restRate, 
            double restProb, double greedyProb) {
        this.shop = new Shop(noOfServers, noOfSelfCheckouts, restProb, length);
        this.gen = new RandomGenerator(seed, arrRate, svcRate, restRate);
        this.events = new PriorityQueue<>(new EventComparator());
        this.stats = new Statistics();

        double time = 0;
        for (int i = 0; i < noOfCustomers; i++) {
            boolean isGreedy = greedyProb > gen.genCustomerType();
            ArrivalEvent tempEvent = new ArrivalEvent(time, isGreedy);
            events.add(tempEvent);
            time += gen.genInterArrivalTime();
        }
    }

    /**
     * Processes the full sequence of ArrivalEvents and calculate the statistics.
     */
    public void simulate() {
        List<String> outputList = new ArrayList<>();
        while (!events.isEmpty()) {
            Event event = events.poll();
            if (!(event instanceof ServerBackEvent) && !(event instanceof ServerRestEvent)) {
                outputList.add(event.toString());
            }
            processEvent(event);
        }
        printOutput(outputList);
    }

    /**
     * Checks the type of Event and performs a certain action accordingly.
     * 
     * @param event Event to be processed.
     */
    private void processEvent(Event event) {
        if (event instanceof ArrivalEvent) {
            serveArrivedEvent((ArrivalEvent) event);
        } else if (event instanceof ServedEvent) {
            serveServedEvent((ServedEvent) event);
        } else if (event instanceof DoneEvent) {
            serveDoneEvent((DoneEvent) event);
        } else if (event instanceof ServerRestEvent) {
            ServerRestEvent serverRestEvent = (ServerRestEvent) event;
            events.add(serverRestEvent.createBackEvent());
        } else if (event instanceof ServerBackEvent) {
            serveBackEvent((ServerBackEvent) event);
        }
    }

    /**
     * Attempt to serve the customer instantly, otherwise find a queue for customer accordingly.
     * 
     * @param event ArrivalEvent to be processed.
     */
    private void serveArrivedEvent(ArrivalEvent event) {
        Optional<? extends Servicer> svc = shop.findToServe(event.getCustomer().getTime());
        svc.ifPresentOrElse(s -> {
            s.serveCustomer(event);
            events.add(event.createServedEvent(s.getId(), s instanceof SelfCheckout));
            stats.custServed();
        }, 
            () -> {
                Customer cs = event.getCustomer();
                if (cs instanceof GreedyCustomer) {
                    serveGreedyCustomer(event);
                } else {
                    serveCustomer(event);
                }
            });
    }

    /**
     * Search for the shortest queue for greedy customer. Otherwise, customer will leave.
     * 
     * @param event ArrivalEvent to be processed.
     */
    private void serveGreedyCustomer(ArrivalEvent event) {
        Optional<? extends Servicer> svc = shop.findForGreedyCust();
        svc.ifPresentOrElse(s -> {
            WaitEvent waitEvent = event.createWaitEvent(s.getId(), s instanceof SelfCheckout);
            s.queueUp(waitEvent);
            events.add(waitEvent);
        }, 
            () -> {
                events.add(event.createLeaveEvent());
                stats.custLeft();
            });
    }

    /**
     * Search for a servicer to queue for typical customer. Otherwise, customer will leave.
     * 
     * @param event ArrivalEvent to be processed.
     */
    private void serveCustomer(ArrivalEvent event) {
        Optional<? extends Servicer> svc = shop.findToWait();
        svc.ifPresentOrElse(s -> {
            WaitEvent waitEvent = event.createWaitEvent(s.getId(), s instanceof SelfCheckout);
            s.queueUp(waitEvent);
            events.add(waitEvent);
        }, 
            () -> {
                events.add(event.createLeaveEvent());
                stats.custLeft();
            });
    }

    /**
     * Generate service time and update the servicer's next available serving time.
     * 
     * @param event ServedEvent to be processed.
     */
    private void serveServedEvent(ServedEvent event) {
        Optional<? extends Servicer> svc = shop.find(event.getServicerId());
        svc.ifPresent(s -> {
            double servingTime = gen.genServiceTime();
            s.setNextServeTime(s.getNextServeTime() + servingTime);
            events.add(event.createDoneEvent(servingTime));
        });
    }

    /**
     * Make the servicer either rest, serve the next waiting customer or available to serve.
     * 
     * @param event DoneEvent to be processed.
     */
    private void serveDoneEvent(DoneEvent event) {
        int servicerId = event.getServicerId();
        Optional<? extends Servicer> svc = shop.find(servicerId);
        svc.ifPresent(s -> {
            boolean doServe = true;
            Customer cs = event.getCustomer();
            if (s instanceof Server) {
                if (gen.genRandomRest() < shop.getRestProbability()) {
                    double returnTime = cs.getTime() + gen.genRestPeriod();
                    s.setNextServeTime(returnTime);
                    events.add(new ServerRestEvent(cs, servicerId, returnTime));
                    doServe = false;
                }
            }
            if (doServe) {
                if (s.hasWaitingCustomers()) {
                    WaitEvent we = s.getWaitingCustomer();
                    stats.addWaitingTime(cs.getTime() - we.getCustomer().getTime());
                    events.add(we.createServedEvent(cs.getTime(), servicerId));
                } else {
                    s.makeIdle();
                }
            }
        });
    }

    /**
     * Make the servicer either rest, serve the next waiting customer or available to serve.
     * 
     * @param event ServerBackEvent to be processed.
     */
    private void serveBackEvent(ServerBackEvent event) {
        Optional<? extends Servicer> svc = shop.find(event.getServerId());
        svc.ifPresent(s -> {
            Customer cs = event.getCustomer();
            if (s.hasWaitingCustomers()) {
                WaitEvent we = s.getWaitingCustomer();
                stats.addWaitingTime(cs.getTime() - we.getCustomer().getTime());
                events.add(we.createServedEvent(cs.getTime()));
            } else {
                s.makeIdle();
            }
        });
    }

    /**
     * Print the Event occurrence sequence and statistics to be displayed to the
     * user.
     * 
     * @param outputList The output list of Events.
     */
    private void printOutput(List<String> outputList) {
        outputList.forEach(System.out::println);
        System.out.println(stats.toString());
    }
}
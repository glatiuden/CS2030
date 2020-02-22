import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This class encapsulates all the simulation states. There are four main
 * components: (i) the event queue, (ii) the statistics, (iii) the shop (the
 * servers) and (iv) the event logs.
 * 
 * @author atharvjoshi
 * @author weitsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */

public class SimState {
  class Event implements Comparable<Event> {
    private double time;
    private Function<SimState, SimState> func;

    public Event(double time, Function<SimState, SimState> func) {
      this.time = time;
      this.func = func;
    }

    public int compareTo(Event other) {
      return (int) Math.signum(this.time - other.time);
    }

    public SimState simulate(SimState sim) {
      return func.apply(sim);
    }

    public String toString() {
      return String.valueOf(time);
    }
  }

  /** The priority queue of events. */
  private final PriorityQueue<Event> events;

  /** The statistics maintained. */
  private final Statistics stats;

  /** The shop of servers. */
  private final Shop shop;

  private final String strOutput;

  /**
   * Constructor for creating the simulation state from scratch.
   * 
   * @param numOfServers The number of servers.
   */
  public SimState(int numOfServers) {
    this.shop = new Shop(numOfServers);
    this.stats = new Statistics();
    this.events = new PriorityQueue<Event>();
    this.strOutput = "";
    Customer.reset();
  }

  public SimState(PriorityQueue<Event> events, Statistics stats, Shop shop, String strOutput) {
    this.shop = shop;
    this.stats = stats;
    this.events = events;
    this.strOutput = strOutput;
  }

  /**
   * Add an event to the simulation's event queue.
   * 
   * @param e The event to be added to the queue.
   * @return The new simulation state.
   */
  public SimState addEvent(double time, Function<SimState, SimState> func) {
    return new SimState(events.add(new Event(time, func)), stats, shop, strOutput);
  }

  /**
   * Retrieve the next event with earliest time stamp from the priority queue, and
   * a new state. If there is no more event, an Optional.empty will be returned.
   * 
   * @return A pair object with an (optional) event and the new simulation state.
   */
  public Pair<Optional<Event>, SimState> nextEvent() {
    Pair<Optional<Event>, PriorityQueue<Event>> result = events.poll();
    return Pair.of(result.first, new SimState(result.second, stats, shop, strOutput));
  }

  /**
   * Log a customer's arrival in the simulation.
   * 
   * @param time The time the customer arrives.
   * @param c    The customer that arrrives.
   * @return A new state of the simulation after the customer arrives.
   */
  public SimState noteArrival(double time, Customer c) {
    return new SimState(events, stats, shop, strOutput + String.format("%.3f %s arrives\n", time, c));
  }

  /**
   * Log when a customer waits in the simulation.
   * 
   * @param time The time the customer starts waiting.
   * @param s    The server the customer is waiting for.
   * @param c    The customer who waits.
   * @return A new state of the simulation after the customer waits.
   */
  public SimState noteWait(double time, Server s, Customer c) {
    return new SimState(events, stats, shop.replace(s),
        strOutput + String.format("%.3f %s waits to be served by %s\n", time, c, s));
  }

  /**
   * Log when a customer is served in the simulation.
   * 
   * @param time The time the customer arrives.
   * @param s    The server that serves the customer.
   * @param c    The customer that is served.
   * @return A new state of the simulation after the customer is served.
   */
  public SimState noteServed(double time, Server s, Customer c) {
    return new SimState(events, stats.serveOneCustomer().recordWaitingTime(c.timeWaited(time)), shop,
        strOutput + String.format("%.3f %s served by %s\n", time, c, s));
  }

  /**
   * Log when a customer is done being served in the simulation.
   * 
   * @param time The time the customer arrives.
   * @param s    The server that serves the customer.
   * @param c    The customer that is served.
   * @return A new state of the simulation after the customer is done being
   *         served.
   */
  public SimState noteDone(double time, Server s, Customer c) {
    return new SimState(this.events, this.stats, this.shop,
        this.strOutput + String.format("%.3f %s done serving by %s\n", time, c, s));
  }

  /**
   * Log when a customer leaves the shops without service.
   * 
   * @param time     The time this customer leaves.
   * @param customer The customer who leaves.
   * @return A new state of the simulation.
   */
  public SimState noteLeave(double time, Customer customer) {
    return new SimState(events, stats.looseOneCustomer(), shop,
        strOutput + String.format("%.3f %s leaves\n", time, customer));
  }

  /**
   * Simulates the logic of what happened when a customer arrives. The customer is
   * either served, waiting to be served, or leaves.
   * 
   * @param time The time the customer arrives.
   * @return A new state of the simulation.
   */
  public SimState simulateArrival(double time) {
    Customer customer = new Customer(time);
    return noteArrival(time, customer).processArrival(time, customer);
  }

  /**
   * Handle the logic of finding idle servers to serve the customer, or a server
   * that the customer can wait for, or leave. Called from simulateArrival.
   * 
   * @param time     The time the customer arrives.
   * @param customer The customer to be served.
   * @return A new state of the simulation.
   */
  public SimState processArrival(double time, Customer customer) {
    return shop.find(server -> server.isIdle()).map(server -> serveCustomer(time, server, customer))
        .or(() -> shop.find(server -> !server.hasWaitingCustomer())
            .map(server -> noteWait(time, server.askToWait(customer), customer)))
        .orElse(noteLeave(time, customer));
  }

  /**
   * Simulate the logic of what happened when a customer is done being served. The
   * server either serve the next customer or becomes idle.
   * 
   * @param time     The time the service is done.
   * @param server   The server serving the customer.
   * @param customer The customer being served.
   * @return A new state of the simulation.
   */
  public SimState simulateDone(double time, Server server, Customer customer) {
    final SimState newSimState = noteDone(time, server, customer);
    return newSimState.shop.find(x -> x.equals(server))
        .map(s -> s.getWaitingCustomer().map(cust -> newSimState.serveCustomer(time, server, cust)).orElse(new SimState(
            newSimState.events, newSimState.stats, newSimState.shop.replace(server.makeIdle()), newSimState.strOutput)))
        .orElse(newSimState);
  }

  /**
   * Handle the logic of server serving customer. A new done event is generated
   * and scheduled.
   * 
   * @param time     The time this customer is served.
   * @param server   The server serving this customer.
   * @param customer The customer being served.
   * @return A new state of the simulation.
   */
  public SimState serveCustomer(double time, Server server, Customer customer) {
    double doneTime = time + Simulation.SERVICE_TIME;
    SimState newSimState = noteServed(time, server, customer).addEvent(doneTime,
        x -> x.simulateDone(doneTime, server, customer));
    return new SimState(newSimState.events, newSimState.stats, newSimState.shop.replace(server.serve(customer)),
        newSimState.strOutput);
  }

  /**
   * The main simulation loop. Repeatedly get events from the event queue,
   * simulate and update the event. Return the final simulation state.
   * 
   * @return The final state of the simulation.
   */
  public SimState run() {
    final Pair<Optional<Event>, SimState> p = nextEvent();
    return Stream.iterate(p, x -> x.first.get().simulate(x.second).nextEvent())
        .dropWhile(x -> x.first.isPresent()).findFirst().orElse(p).second;
  }

  /**
   * Return a string representation of the simulation state, which consists of all
   * the logs and the stats.
   * 
   * @return A string representation of the simulation.
   */
  public String toString() {
    return strOutput + stats.toString();
  }
}

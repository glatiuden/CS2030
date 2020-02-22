import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Iterator;;

public class Simulator {
    public static final double SERVICE_TIME = 1.0; // Time spent serving a customer
    public static int noOfCustServed = 0;
    public static int noOfCustLeft = 0;
    public static double amtOfWaitingTime = 0;
    public static Server[] servers;

    public static void simulate() {
        PriorityQueue<Event> queue = new PriorityQueue<>(new EventComparator());

        Scanner sc = new Scanner(System.in);
        int noOfServers = sc.nextInt();
        servers = new Server[noOfServers];
        for (int i = 1; i <= noOfServers; i++) {
            servers[i - 1] = new Server(i);
        }

        while (sc.hasNextDouble()) {
            ArrivalEvent event = new ArrivalEvent(sc.nextDouble());
            queue.add(event);
        }
        sc.close();
        printOutput(queue);

        System.out.println(
                String.format("[%.3f %d %d]", (amtOfWaitingTime / noOfCustServed), noOfCustServed, noOfCustLeft));

    }

    public static void printOutput(PriorityQueue<Event> queue) {
        List<String> printables = new ArrayList<>();
        List<Event> waitingList = new ArrayList<>();

        while (!queue.isEmpty()) {
            Event currentEvent = queue.poll();
            printables.add(currentEvent.toString());

            if (currentEvent instanceof ArrivalEvent) {
                serveArrivalCustomer(currentEvent, waitingList, queue);
            } else if (currentEvent instanceof ServedEvent) {
                ServedEvent servedEvent = (ServedEvent) currentEvent;
                queue.add(new DoneEvent(servedEvent, servedEvent.getServerId()));
            } else if (currentEvent instanceof DoneEvent) {
                DoneEvent doneEvent = (DoneEvent) currentEvent;
                if (servers[doneEvent.getServerId() - 1].getWait() == true) {
                    serveWaitingCustomer(waitingList, queue, doneEvent.getServerId());
                }
            }
        }

        for (String s : printables) {
            System.out.println(s);
        }
    }

    private static void serveWaitingCustomer(List<Event> waitingList, PriorityQueue<Event> queue, int serverId) {
        if (waitingList.size() > 0) {
            Iterator<Event> waitingListIterator = waitingList.iterator();
            while (waitingListIterator.hasNext()) {
                WaitEvent e1 = (WaitEvent) waitingListIterator.next();

                if (serverId == e1.getServerId()) {
                    Server assignedServer = servers[serverId - 1];
                    queue.add(new ServedEvent(e1.getId(), assignedServer.getNextServingTime(), e1.getServerId()));
                    amtOfWaitingTime += (assignedServer.getNextServingTime() - e1.getTime());
                    assignedServer.setNextServingTime(assignedServer.getNextServingTime() + SERVICE_TIME);
                    assignedServer.setWait(false);
                    servers[serverId - 1] = assignedServer;
                    noOfCustServed++;
                    waitingListIterator.remove();
                    break;
                }
            }
        }
    }

    private static void serveArrivalCustomer(Event currentEvent, List<Event> waitingList, PriorityQueue<Event> queue) {
        Server server = getAvailableServer(currentEvent.getTime());
        if (!server.isEmpty()) {
            server.setNextServingTime(currentEvent.getTime() + SERVICE_TIME);
            queue.add(new ServedEvent(currentEvent, server.getServerId()));
            noOfCustServed++;
        } else {
            server = getCanWaitServer();
            if (!server.isEmpty()) {
                waitingList.add(new WaitEvent(currentEvent, server.getServerId()));
                queue.add(new WaitEvent(currentEvent, server.getServerId()));
                servers[server.getServerId() - 1].setWait(true);
            } else {
                queue.add(new LeaveEvent(currentEvent));
                noOfCustLeft++;
            }
        }
    }

    public static Server getAvailableServer(double timeArrival) {
        Server selectServer = new Server();
        for (Server server : servers) {
            if (server.getNextServingTime() == 0 && !server.getWait()) {
                selectServer = server;
                break;
            } else if (timeArrival >= server.getNextServingTime() && !server.getWait()) {
                selectServer = server;
                break;
            }
        }
        return selectServer;

    }

    public static Server getCanWaitServer() {
        Server selectServer = new Server();
        for (Server server : servers) {
            if (!server.getWait()) {
                selectServer = server;
                break;
            }
        }
        return selectServer;
    }
}

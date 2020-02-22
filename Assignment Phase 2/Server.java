package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;

/**
 * The Server class keeps track of who is the customer waiting to be served (if
 * any).
 */
class Server extends Servicer {
    /**
     * Customers who are currently queuing to be served by a given server, if any.
     */
    private final Queue<WaitEvent> waitingCustomers;

    Server(int id) {
        super(id);
        this.waitingCustomers = new LinkedList<WaitEvent>();
    }

    /**
     * Checks whether the given server can have more waiting customers.
     * 
     * @return A boolean representing whether the given server can have more waiting
     *         customers.
     */
    @Override
    boolean hasWaitingSlot() {
        return this.waitingCustomers.size() < maxQueue;
    }

    /**
     * Checks whether the given server have any waiting customer.
     * 
     * @return A boolean representing whether the given server have waiting
     *         customer.
     */
    @Override
    boolean hasWaitingCustomers() {
        return this.waitingCustomers.size() != 0;
    }

    /**
     * Returns WaitEvent for given server in the queue.
     * 
     * @return The WaitEvent waiting for given server.
     */
    @Override
    WaitEvent getWaitingCustomer() {
        return this.waitingCustomers.poll();
    }

    /**
     * Returns the number of customers in the queue for the given server.
     * 
     * @return An integer representing number of customers in the queue for the
     *         given server.
     */
    @Override
    int getQueueLength() {
        return this.waitingCustomers.size();
    }

    /**
     * Make a customer wait for this server.
     * 
     * @param we The WaitEvent who will wait for this server.
     */
    @Override
    void queueUp(WaitEvent we) {
        this.waitingCustomers.offer(we);
    }
}
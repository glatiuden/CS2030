package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;

/**
 * The SelfCheckout class keeps track of who is the customer waiting to be
 * served in a common queue (if any).
 */
class SelfCheckout extends Servicer {
    /**
     * Customers who are currently queuing to be served by self-checkout counters,
     * if any.
     */
    private static final Queue<WaitEvent> WAITING_CUSTOMERS = new LinkedList<WaitEvent>();

    /**
     * Creates a self-checkout and initalizes it with an ID.
     * 
     * @param id The unique ID of this self-checkout.
     */
    SelfCheckout(int id) {
        super(id);
    }

    /**
     * Checks whether the self-checkout counters can have more waiting customers.
     * 
     * @return A boolean representing whether the self-checkout counters can have
     *         more waiting customers.
     */
    @Override
    boolean hasWaitingSlot() {
        return WAITING_CUSTOMERS.size() < maxQueue;
    }

    /**
     * Checks whether the self-checkout counters have any waiting customer.
     * 
     * @return A boolean representing whether the given self-checkout counters have
     *         waiting customer.
     */
    @Override
    boolean hasWaitingCustomers() {
        return WAITING_CUSTOMERS.size() != 0;
    }

    /**
     * Returns WaitEvent who is in the queue for self-checkout counters.
     * 
     * @return The WaitEvent waiting for self-checkout counters.
     */
    @Override
    WaitEvent getWaitingCustomer() {
        return WAITING_CUSTOMERS.poll();
    }

    /**
     * Returns the number of customers in the queue for self-checkout.
     * 
     * @return An integer representing number of customers in the queue for
     *         self-checkout.
     */
    @Override
    int getQueueLength() {
        return WAITING_CUSTOMERS.size();
    }

    /**
     * Make a customer wait for self-checkout counters.
     * 
     * @param we The WaitEvent who will wait for self-checkout counters.
     */
    @Override
    void queueUp(WaitEvent we) {
        WAITING_CUSTOMERS.offer(we);
    }
}
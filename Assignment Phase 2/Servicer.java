package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;

/**
 * The Servicer abstract class is used as the parent class of Server and
 * SelfCheckout.
 */
abstract class Servicer {
    /** The unique ID of this Servicer. */
    private final int id;
    /** The next available serving time of this servicer. */
    private double serveTime;
    /** The customer currently being served, if any. */
    private Optional<Event> currentCustomer;
    /** The maximum queue length of a servicer. */
    protected static int maxQueue = 0;

    /**
     * Creates a servicer and initalizes it with an ID.
     * 
     * @param id The unique ID of this servicer.
     */
    Servicer(int id) {
        this.id = id;
        this.serveTime = 0;
        this.currentCustomer = Optional.empty();
    }

    /**
     * Gets the unique ID of the servicer.
     * 
     * @return An integer representing the ID of the servicer.
     */
    int getId() {
        return this.id;
    }

    /**
     * Set the servicer's queue length.
     * 
     * @param queueLength The maximum queue length of a servicer.
     */
    static void setMaxQueue(int queueLength) {
        maxQueue = queueLength;
    }

    /**
     * Checks if the current servicer is idle.
     * 
     * @return true if the servicer is idle (no current customer); false otherwise.
     */
    boolean isIdle() {
        return this.currentCustomer.isEmpty();
    }

    /**
     * Change this servicer's state to idle by removing its current customer.
     */
    void makeIdle() {
        this.currentCustomer = Optional.empty();
    }

    /**
     * Serve an event (customer).
     * 
     * @param e The event to be served.
     */
    void serveCustomer(Event e) {
        this.currentCustomer = Optional.of(e);
    }

    /**
     * Gets the servicer's next available serving time.
     * 
     * @return A double value representing the servicer's next available serving
     *         time.
     */
    double getNextServeTime() {
        return this.serveTime;
    }

    /**
     * Changes the servicer's next available serving time.
     * 
     * @param time The servicer's next available serving time.
     */
    void setNextServeTime(double time) {
        this.serveTime = time;
    }

    /**
     * Checks whether the given servicer can have more waiting customers.
     * 
     * @return A boolean representing whether the given server can have more waiting
     *         customers.
     */
    abstract boolean hasWaitingSlot();

    /**
     * Checks whether the given servicer have any waiting customer.
     * 
     * @return A boolean representing whether the given server have waiting
     *         customer.
     */
    abstract boolean hasWaitingCustomers();

    /**
     * Returns WaitEvent for given servicer in the queue.
     * 
     * @return The WaitEvent waiting for given server.
     */
    abstract WaitEvent getWaitingCustomer();

    /**
     * Returns the number of customers in the queue for the given servicer.
     * 
     * @return An integer representing number of customers in the queue for the
     *         given servicer.
     */
    abstract int getQueueLength();

    /**
     * Make a customer wait for this servicer.
     * 
     * @param we The WaitEvent who will wait for this servicer.
     */
    abstract void queueUp(WaitEvent we);
}
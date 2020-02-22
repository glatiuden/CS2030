package cs2030.simulator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Customer class encapsulates information and methods pertaining to a
 * Customer in a simulation.
 */
class Customer {
    /** The unique ID of the last created customer generated from AtomicInteger. */
    private static AtomicInteger counter = new AtomicInteger(0);
    /** The unique ID of this customer. */
    protected final int id;
    /** The time this customer arrives. */
    protected final double time;

    /**
     * Create and initalize a new customer.
     * 
     * @param id   The ID of the customer.
     * @param time The time that an event occured on the customer.
     */
    Customer(int id, double time) {
        this.id = id;
        this.time = time;
    }

    /**
     * Create and initalize a new customer. The {@code id} of the customer is set.
     *
     * @param time The time that an event occured on the customer.
     */
    Customer(double time) {
        this.id = counter.incrementAndGet();
        this.time = time;
    }

    /**
     * Return the ID of the customer.
     * 
     * @return An integer representing the ID of the customer.
     */
    int getId() {
        return this.id;
    }

    /**
     * Return the time that an event occured on this customer .
     * 
     * @return An double representing the time that an event occured on the
     *         customer.
     */
    double getTime() {
        return this.time;
    }

    /**
     * Return a string representation of this customer.
     * 
     * @return A string representation of this customer.
     */
    @Override
    public String toString() {
        return String.format("%.3f %d", this.time, this.id);
    }
}
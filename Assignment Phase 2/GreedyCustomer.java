package cs2030.simulator;

/**
 * The Greedy Customer class extends from the Customer class to distinguish
 * between the type of customers.
 */
class GreedyCustomer extends Customer {
    /**
     * Create and initalize a new greedy customer.
     * 
     * @param id   The ID of the customer.
     * @param time The time that an event occured on the customer.
     */
    GreedyCustomer(int id, double time) {
        super(id, time);
    }

    /**
     * Create and initalize a new greedy customer. The {@code id} of the customer is
     * set.
     *
     * @param time The time that an event occured on the customer.
     */
    GreedyCustomer(double time) {
        super(time);
    }

    /**
     * Return a string representation of this greedy customer.
     * 
     * @return A string representation of this greedy customer.
     */
    @Override
    public String toString() {
        return String.format("%.3f %d(greedy)", this.time, this.id);
    }
}

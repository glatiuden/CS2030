package cs2030.simulator;

/**
 * The WaitEvent class to simulate the act of customer waiting to be served by an
 * assigned servicer.
 */
class WaitEvent extends Event {
    /**
     * The identifier of the servicer that the WaitEvent belongs to.
     */
    private final int servicerId;
    /**
     * A boolean used to identify whether is the event served by Self-Checkout
     * Counter.
     */
    private final boolean isSC;

    /**
     * Creates a WaitEvent.
     * 
     * @param cs         The customer object of the WaitEvent.
     * @param servicerId The ID of the servicer who customer is waiting for.
     * @param isSC       A boolean used to identify whether is the event served by
     *                   Self-Checkout Counter.
     */
    WaitEvent(Customer cs, int servicerId, boolean isSC) {
        super(cs);
        this.servicerId = servicerId;
        this.isSC = isSC;
    }

    /**
     * Gets the ID of the assigned servicer.
     * 
     * @return An integer representing the ID of the servicer.
     */
    int getServicerId() {
        return this.servicerId;
    }

    /**
     * Gets an integer representation of WaitEvent.
     * 
     * @return An integer representing the type of the event.
     */
    @Override
    int getStatus() {
        return EventType.WAITS.getValue();
    }

    /**
     * Creates a ServedEvent bounded after the waiting servicer is free.
     * 
     * @param sTime The time the waiting customer is being served.
     * @return ServedEvent.
     */
    ServedEvent createServedEvent(double sTime) {
        return new ServedEvent(
                cs instanceof GreedyCustomer ? 
                new GreedyCustomer(cs.getId(), sTime) : new Customer(cs.getId(), sTime),
                this.servicerId, this.isSC);
    }

    /**
     * Creates a ServedEvent bounded after the waiting servicer is free. This method
     * is used for self-checkout counters.
     * 
     * @param servicerId The ID of the servicer who is available to serve.
     * @param sTime      The time the waiting customer is being served.
     * @return ServedEvent.
     */
    ServedEvent createServedEvent(double sTime, int servicerId) {
        return new ServedEvent(
                cs instanceof GreedyCustomer ? 
                new GreedyCustomer(cs.getId(), sTime) : new Customer(cs.getId(), sTime),
                servicerId, this.isSC);
    }

    /**
     * Return a string representation of the WaitEvent, which consists of time, id
     * and the action.
     * 
     * @return A string representation of the WaitEvent.
     */
    @Override
    public String toString() {
        return String.format("%s waits to be served by %s %d", super.cs.toString(), 
            this.isSC ? "self-check" : "server", this.servicerId);
    }
}
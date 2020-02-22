package cs2030.simulator;

/**
 * The ServedEvent class to simulate the act of customer is being served by a
 * servicer.
 */
class ServedEvent extends Event {
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
     * Creates a ServedEvent.
     * 
     * @param cs         The customer object of the ServedEvent.
     * @param servicerId The ID of the servicer that is serving the ServedEvent.
     * @param isSC       A boolean used to identify whether is the event served by
     *                   Self-Checkout Counter.
     */
    ServedEvent(Customer cs, int servicerId, boolean isSC) {
        super(cs);
        this.servicerId = servicerId;
        this.isSC = isSC;
    }

    /**
     * Gets the identifier of the serving servicer.
     * 
     * @return An integer representing the ID of the servicer.
     */
    int getServicerId() {
        return this.servicerId;
    }

    /**
     * Creates a DoneEvent after the ServedEvent is completed.
     * 
     * @param sTime The time where the customer is serve.
     * @return DoneEvent.
     */
    DoneEvent createDoneEvent(double sTime) {
        if (cs instanceof GreedyCustomer) {
            return new DoneEvent(new GreedyCustomer(cs.getId(), cs.getTime() + sTime), 
                this.servicerId, this.isSC);
        } else {
            return new DoneEvent(new Customer(cs.getId(), cs.getTime() + sTime), 
                this.servicerId, this.isSC);
        }
    }

    /**
     * Gets an integer representation of ServedEvent.
     * 
     * @return An integer representing the type of event.
     */
    @Override
    int getStatus() {
        return EventType.SERVED.getValue();
    }

    /**
     * Formats the ServedEvent to print out its information.
     */
    @Override
    public String toString() {
        return String.format("%s served by %s %d", super.cs.toString(), 
            this.isSC ? "self-check" : "server", this.servicerId);
    }
}
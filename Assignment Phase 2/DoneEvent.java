package cs2030.simulator;

/**
 * The DoneEvent class to simulate the act of Event completion.
 */
class DoneEvent extends Event {
    /**
     * The ID of the server that the WaitEvent belongs to.
     */
    private final int servicerId;
    /**
     * A boolean used to identify whether is the event served by Self-Checkout
     * Counter.
     */
    private final boolean isSC;

    /**
     * Creates a DoneEvent.
     * 
     * @param cs            The customer object which contains the ID and time.
     * @param servicerId    The ID of the servicer who is served the customer.
     * @param isSC          A boolean used to identify whether is the servicer a
     *                      self-checkout counter.
     */
    DoneEvent(Customer cs, int servicerId, boolean isSC) {
        super(cs);
        this.servicerId = servicerId;
        this.isSC = isSC;
    }

    /**
     * Gets the ID of the servicer.
     * 
     * @return An integer representing the ID of the servicer.
     */
    int getServicerId() {
        return this.servicerId;
    }

    /**
     * Gets an integer representation of DoneEvent.
     * 
     * @return An integer representing the type of the event.
     */
    @Override
    int getStatus() {
        return EventType.DONE.getValue();
    }

    /**
     * Return a string representation of the DoneEvent, which consists of time, id
     * and servicer's ID.
     * 
     * @return A string representation of the DoneEvent.
     */
    @Override
    public String toString() {
        return String.format("%s done serving by %s %d", super.cs.toString(), 
            this.isSC ? "self-check" : "server", this.servicerId);
    }
}
package cs2030.simulator;

/**
 * The ArrivalEvent class to simulate the act of customer arrival.
 */
class ArrivalEvent extends Event {
    /**
     * Creates an ArrivalEvent.
     * 
     * @param time     The time that the ArrivalEvent created.
     * @param isGreedy A boolean to identify whether is the customer greedy.
     */
    ArrivalEvent(double time, boolean isGreedy) {
        super(isGreedy ? new GreedyCustomer(time) : new Customer(time));
    }

    /**
     * Creates a ServedEvent bounded to an available servicer.
     * 
     * @param servicerId The ID of the servicer who is available to serve.
     * @param isSC       A boolean used to identify whether is the servicer a
     *                   self-checkout counter.
     * @return ServedEvent.
     */
    ServedEvent createServedEvent(int servicerId, boolean isSC) {
        return new ServedEvent(cs, servicerId, isSC);
    }

    /**
     * Creates a WaitEvent bounded to a servicer who is serving another customer.
     * 
     * @param servicerId The ID of the servicer who the customer is waiting for.
     * @param isSC       A boolean used to identify whether is the servicer a
     *                   self-checkout counter.
     * @return WaitEvent.
     */
    WaitEvent createWaitEvent(int servicerId, boolean isSC) {
        return new WaitEvent(cs, servicerId, isSC);
    }

    /**
     * Creates a LeaveEvent if all servicers are unavailable and unable to have a
     * waiting customer.
     * 
     * @return LeaveEvent.
     */
    LeaveEvent createLeaveEvent() {
        return new LeaveEvent(cs);
    }

    /**
     * Gets an integer representation of ArrivalEvent.
     * 
     * @return An integer representing the type of the event.
     */
    @Override
    int getStatus() {
        return EventType.ARRIVES.getValue();
    }

    /**
     * Return a string representation of the ArrivalEvent, which consists of time,
     * ID and the action.
     * 
     * @return A string representation of the ArrivalEvent.
     */
    @Override
    public String toString() {
        return String.format("%s arrives", super.cs.toString());
    }
}
package cs2030.simulator;

/**
 * The LeaveEvent class to simulate the act of customer leaves the shop when all
 * the servicers are unavailable.
 */
class LeaveEvent extends Event {
    /**
     * Creates a LeaveEvent.
     * 
     * @param cs The customer object which contains the ID and time.
     */
    public LeaveEvent(Customer cs) {
        super(cs);
    }

    /**
     * Gets an integer representation of LeaveEvent.
     * 
     * @return An integer representing the type of event.
     */
    @Override
    public int getStatus() {
        return EventType.LEAVES.getValue();
    }

    /**
     * Return a string representation of the LeaveEvent, which consists of time, id
     * and the action.
     * 
     * @return A string representation of the LeaveEvent.
     */
    @Override
    public String toString() {
        return String.format("%s leaves", super.cs.toString());
    }
}
package cs2030.simulator;

/**
 * The Event abstract class that contains event's information.
 */
abstract class Event {
    /**
     * A customer object used to store the identifier and time.
     */
    protected final Customer cs;

    /**
     * Creates an Event.
     * 
     * @param cs The customer object which contains the ID and time.
     */
    protected Event(Customer cs) {
        this.cs = cs;
    }

    /**
     * Gets the customer object bounded to the Event.
     * 
     * @return An customer object bounded to the Event.
     */   
    protected Customer getCustomer() {
        return cs;
    }

    /**
     * Checks whether if the current event occurs later compared to another Event.
     * 
     * @param e2 the Event to be compared to.
     * @return true if the current Event occurs later, otherwise false.
     */
    boolean isAfter(Event e2) {
        return this.cs.getTime() > e2.cs.getTime();
    }

    /**
     * Checks whether if the current Event bigger ID in terms of number compared to
     * another Event.
     * 
     * @param e2 the Event to be compared to.
     * @return true if the current Event has bigger Id in terms of number, otherwise
     *         false.
     */
    boolean hasBiggerId(Event e2) {
        return this.cs.getId() > e2.cs.getId();
    }

    /**
     * If the eventId is the same, checks whether if the current Event has a higher
     * type compared to another Event.
     * 
     * @param e2 the Event to be compared to.
     * @return true if the current has a higher type, otherwise false.
     */
    boolean hasHigherType(Event e2) {
        if (this.cs.getId() == e2.cs.getId()) {
            return this.getStatus() > e2.getStatus();
        } else {
            return false;
        }
    }

    /**
     * Gets an integer representation of the type of the Event.
     * 
     * @return An integer representing the type of the Event.
     */
    abstract int getStatus();
}
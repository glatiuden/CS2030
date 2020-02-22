package cs2030.simulator;

/**
 * The EventType enum class that contains the different integer representation of
 * EventType.
 */
enum EventType {
    /**
     * ArrivalEvent.
     */
    ARRIVES(1),
    /**
     * ServedEvent.
     */
    SERVED(2),
    /**
     * LeaveEvent.
     */
    LEAVES(3),
    /**
     * DoneEvent.
     */
    DONE(4),
    /**
     * WaitEvent.
     */
    WAITS(5);

    /**
     * Integer presentation of the type of event.
     */
    private final int eventType;

    /**
     * Retrieve the particular EventType.
     * 
     * @param eventType EventType integer presentation.
     */
    EventType(int eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the EventType value.
     * 
     * @return The integer value of particular EventType.
     */
    final int getValue() {
        return eventType;
    }
}
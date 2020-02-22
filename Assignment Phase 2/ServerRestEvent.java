package cs2030.simulator;

/**
 * The ServerRestEvent class to simulate the act of server taking a rest.
 */
class ServerRestEvent extends Event {
    /**
     * The identifier of the server that the ServerRestEvent belongs to.
     */
    private final int serverId;
    /**
     * The time that the server returns after taking a rest.
     */
    private final double returnTime;

    /**
     * Creates a ServerRestEvent.
     * 
     * @param cs         The customer object of the ServerRestEvent.
     * @param serverId   The identifier of the server that the ServerRestEvent
     *                   belongs to.
     * @param returnTime The time that the server returns from taking a break.
     */
    ServerRestEvent(Customer cs, int serverId, double returnTime) {
        super(cs);
        this.serverId = serverId;
        this.returnTime = returnTime;
    }

    /**
     * Gets the ID of the server.
     * 
     * @return An integer representing the ID of the server.
     */
    int getServerId() {
        return this.serverId;
    }

    /**
     * Creates a ServerBackEvent after the server back from break.
     * 
     * @return ServerBackEvent.
     */
    ServerBackEvent createBackEvent() {
        return new ServerBackEvent(new Customer(cs.getId(), returnTime), this.serverId);
    }

    /**
     * Gets an integer representation of ServerRestEvent. ServerRestEvent is not a
     * valid Event type, thus returning 0.
     * 
     * @return An integer representing the type of event.
     */
    @Override
    int getStatus() {
        return 0;
    }
}
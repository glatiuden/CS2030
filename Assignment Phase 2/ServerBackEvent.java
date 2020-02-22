package cs2030.simulator;

/**
 * The ServerBackEvent class to simulate the act of server returning from break.
 */
class ServerBackEvent extends Event {
    /**
     * The ID of the server that the ServerBackEvent belongs to.
     */
    private final int serverId;

    /**
     * Creates a ServerBackEvent.
     * 
     * @param cs       The customer object of the ServerBackEvent.
     * @param serverId The ID of the server who has returned from break.
     */
    ServerBackEvent(Customer cs, int serverId) {
        super(cs);
        this.serverId = serverId;
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
     * Creates a ServedEvent after the server has returned from break.
     * 
     * @param custId The ID of the customer the server is going to serve upon
     *               returning.
     *
     * @return ServedEvent.
     */
    ServedEvent createServedEvent(int custId) {
        return new ServedEvent(new Customer(custId, this.cs.getTime()), 
            this.serverId, false);
    }

    /**
     * Gets an integer representation of ServerBackEvent. ServerBackEvent is not a
     * valid Event type, thus returning 0.
     * 
     * @return An integer representing the type of event.
     */
    @Override
    int getStatus() {
        return 0;
    }
}
public class ServedEvent extends Event {
    private int serverId;

    public ServedEvent(double time, int serverId) {
        super(time);
        super.eventType = SERVED;
        this.serverId = serverId;
    }

    public ServedEvent(Event event, int serverId) {
        super(event.getId(), event.getTime());
        super.eventType = SERVED;
        this.serverId = serverId;
    }

    public ServedEvent(int id, double servedTime, int serverId) {
        super(id, servedTime);
        super.eventType = SERVED;
        this.serverId = serverId;
    }

    public int getEventType() {
        return super.eventType;
    }

    public int getServerId() {
        return this.serverId;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d served by %d", super.time, super.id, this.serverId);
    }
}
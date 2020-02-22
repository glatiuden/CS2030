public class WaitEvent extends Event {
    public int serverId;

    public WaitEvent(double time, int serverId) {
        super(time);
        super.eventType = WAITS;
        this.serverId = serverId;
    }

    public WaitEvent(Event event, int serverId) {
        super(event.getId(), event.getTime());
        super.eventType = WAITS;
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
        return String.format("%.3f %d waits to be served by %d", super.time, super.id, this.serverId);
    }
}
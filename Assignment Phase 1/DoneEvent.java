public class DoneEvent extends Event {
    public int serverId;

    public DoneEvent(double time, int serverId) {
        super(time + 1);
        super.eventType = DONE;
        this.serverId = serverId;
    }

    public DoneEvent(Event event, int serverId) {
        super(event.getId(), event.getTime() + 1);
        super.eventType = DONE;
        this.serverId = serverId;
    }

    public DoneEvent(int id, double completionTime, int serverId) {
        super(id, completionTime);
        super.eventType = DONE;
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
        return String.format("%.3f %d done serving by %d", super.time, super.id, this.serverId);
    }
}
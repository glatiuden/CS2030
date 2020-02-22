public class LeaveEvent extends Event {
    public LeaveEvent(double time) {
        super(time);
        super.eventType = LEAVES;
    }

    public LeaveEvent(Event event) {
        super(event.getId(), event.getTime());
    }

    public int getEventType() {
        return super.eventType;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d leaves", this.time, this.id);
    }
}
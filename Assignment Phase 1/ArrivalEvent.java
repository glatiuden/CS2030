public class ArrivalEvent extends Event {
    public ArrivalEvent(double time) {
        super(time);
        super.eventType = ARRIVES;
    }

    public ArrivalEvent(Event event) {
        super(event.getId(), event.getTime());
    }

    public int getEventType() {
        return super.eventType;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d arrives", super.time, super.id);
    }
}
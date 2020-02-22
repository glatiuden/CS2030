class Event {
    public static final int DEFAULT = 0;
    public static final int ARRIVES = 1;
    public static final int SERVED = 2;
    public static final int LEAVES = 3;
    public static final int DONE = 4;
    public static final int WAITS = 5;

    private static int counter = 1; // keeping track of no. of items
    protected int id; // id of the event
    protected double time; // The time this event will occur
    protected int eventType; // The type of event, indicates what should happen when an event occurs.

    public Event(double time) {
        this.id = counter;
        this.time = time;
        this.eventType = DEFAULT;
        counter++;
    }

    public Event(int id, double time) {
        this.id = id;
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

    public int getId() {
        return this.id;
    }

    public int getEventType() {
        return this.eventType;
    }

    @Override
    public String toString() {
        return String.format("%d arrives at %.3f", this.id, this.time);
    }
}
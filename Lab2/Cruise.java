public class Cruise {
    private final String cruiseId;
    private final int timeOfArrival;

    public Cruise(String cruiseId, int timeOfArrival) {
        this.cruiseId = cruiseId;
        this.timeOfArrival = timeOfArrival;
    }

    public int getArrivalTime() {
        // String formattedStr = String.format("%04d", this.timeOfArrival);
        // System.out.println(min);
        // int hours = Integer.parseInt(formattedStr.substring(0, 2));
        // int mins = Integer.parseInt(formattedStr.substring(2, 4));
        // return (hours * 60) + mins;
        int mins = timeOfArrival % 100;
        int newHour = timeOfArrival - mins;
        int time = (newHour / 100) * 60 + mins;
        return time;
    }

    public int getNumLoadersRequired() {
        return 1;
    }

    public int getServiceCompletionTime() {
        return this.getArrivalTime() + 30;
    }

    @Override
    public String toString() {
        return String.format("%s@%04d", this.cruiseId, this.timeOfArrival);
    }
}

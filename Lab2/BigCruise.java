public class BigCruise extends Cruise {
    private final int noOfLoaders;
    private final int timeRequired;

    public BigCruise(String cruiseId, int timeOfArrival, int noOfLoaders, int timeRequired) {
        super(cruiseId, timeOfArrival);
        this.noOfLoaders = noOfLoaders;
        this.timeRequired = timeRequired;
    }

    @Override
    public int getNumLoadersRequired() {
        return this.noOfLoaders;
    }

    @Override
    public int getServiceCompletionTime() {
        return super.getArrivalTime() + timeRequired;
    }
}

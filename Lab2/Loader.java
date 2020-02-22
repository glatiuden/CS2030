public class Loader {
    protected final int loaderId;
    protected Cruise cruise;

    public Loader(int loaderId) {
        this.loaderId = loaderId;
    }

    public Loader(int loaderId, Cruise cruise) {
        this.loaderId = loaderId;
        this.cruise = cruise;
    }

    @Override
    public String toString() {
        if (cruise == null)
            return "Loader " + this.loaderId;
        else
            return "Loader " + this.loaderId + " serving " + this.cruise;
    }

    public Loader serve(Cruise cruise) {
        if (this.cruise == null) {
            return new Loader(this.loaderId, cruise);
        } else {
            int newCruiseTime = cruise.getArrivalTime();
            if (newCruiseTime >= this.cruise.getServiceCompletionTime())
                return new Loader(this.loaderId, cruise);
            else
                return null;
        }
    }
}

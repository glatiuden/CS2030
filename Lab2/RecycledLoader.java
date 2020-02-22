public class RecycledLoader extends Loader {

    public RecycledLoader(int loaderId, Cruise cruise) {
        super(loaderId, cruise);
    }

    @Override
    public String toString() {
        if (cruise == null)
            return "Loader " + this.loaderId;
        else
            return "Loader " + this.loaderId + " (recycled) serving " + this.cruise;
    }

    @Override
    public RecycledLoader serve(Cruise cruise) {
        if (this.cruise == null) {
            return new RecycledLoader(this.loaderId, cruise);
        } else {
            int newCruiseTime = cruise.getArrivalTime();
            if (super.cruise.getServiceCompletionTime() + 60 <= newCruiseTime)
                return new RecycledLoader(this.loaderId, cruise);
            else
                return null;
        }
    }
}
public abstract class Ride
{
    protected final int fare;
   
    protected Ride(int fare)
    {
        this.fare = fare;
    }

    public abstract int computeFare(Request request);

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName();
    }
}

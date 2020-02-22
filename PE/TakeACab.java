public class TakeACab extends Ride
{
    private static final int FARE = 33;
    private static final int BOOKINGFARE = 200;
    private int bookingFare;

    public TakeACab()
    {
        super(FARE);
        this.bookingFare = BOOKINGFARE;
    }

    @Override
    public int computeFare(Request request)
    {
       return request.getDistance() * super.fare + this.bookingFare;     
    }
}

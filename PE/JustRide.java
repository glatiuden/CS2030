public class JustRide extends Ride
{
    private static final int SURCHARGE = 500;
    private static final int FARE = 22;
    private int surcharge;

    public JustRide()
    {
        super(FARE);
        this.surcharge = SURCHARGE;
    }

    @Override
    public int computeFare(Request request)
    {
        int time = request.getTime();
        if(time >= 600 && time <= 900)
        {
            return request.getDistance() * super.fare + this.surcharge;
        }
        else {
            return request.getDistance() * super.fare;
        }
    }
}

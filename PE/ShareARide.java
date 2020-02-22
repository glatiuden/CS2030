public class ShareARide extends Ride
{
    private static final int SURCHARGE = 500;
    private static final int FARE = 50;
    private int surcharge;

    public ShareARide()
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
            return (request.getDistance() * super.fare + this.surcharge) / request.getNoOfPassenger();
        }
        else {
            return (request.getDistance() * this.fare) / request.getNoOfPassenger();
        }

    }
}

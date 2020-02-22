public class Booking implements Comparable<Booking>
{
    private Car bookedCar;
    private Ride typeOfRide;
    private Request request;
    private double totalFare;

    public Booking(Car bookedCar, Ride typeOfRide, Request request)
    {
        this.bookedCar = bookedCar;
        this.typeOfRide = typeOfRide;
        this.request = request;
        this.totalFare = (double)(this.typeOfRide.computeFare(request)) / 100;
    }

    @Override
    public String toString()
    {
       return String.format("$%.2f using %s (%s)", this.totalFare, this.bookedCar.toString(), this.typeOfRide.toString());
    }

    @Override
    public int compareTo(Booking b2)
    {
        if(this.totalFare < b2.totalFare)
        {
            return -1;
        } 
        else if(this.totalFare > b2.totalFare)
        {
            return 1;
        }
        else if(this.totalFare == b2.totalFare)
        {
            if(this.bookedCar.getWaitingTime() < b2.bookedCar.getWaitingTime())
                return -1;
            else if(this.bookedCar.getWaitingTime() > b2.bookedCar.getWaitingTime())
                return 1;
            else
                return 0;
        }
        else
        {
            return 0;
        }
    }
}

public class Request
{
    private int distance;
    private int noOfPassenger;
    private int time;

    public Request(int distance, int noOfPassenger, int time)
    {
        this.distance = distance;
        this.noOfPassenger = noOfPassenger;
        this.time = time;
    }

    public int getDistance()
    {
        return this.distance;
    }

    public int getTime()
    {
        return this.time;
    }

    public int getNoOfPassenger()
    {
        return this.noOfPassenger;
    }

    @Override
    public String toString()
    {
        return String.format("%dkm for %dpax @ %dhrs", this.distance, this.noOfPassenger, this.time);
    }
}

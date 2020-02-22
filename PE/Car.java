public abstract class Car
{
    private String licenseNoPlate;
    private int waitingTime;

    public Car(String licenseNoPlate, int waitingTime)
    {
        this.licenseNoPlate = licenseNoPlate;
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime()
    {
        return this.waitingTime;
    }

    @Override
    public String toString()
    {
        return String.format("%s (%d mins away) %s", this.licenseNoPlate, this.waitingTime, this.getClass().getSimpleName());
    }
}

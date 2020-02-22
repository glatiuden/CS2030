package cs2030.simulator;

/**
 * The Statistics class to keep track of the total waiting time of customers, the
 * number of customers who left without being served, the number of customers
 * who are served.
 */
class Statistics {
    /**
     * Total number of customers served.
     */
    private int noOfCustServed;
    /**
     * Total number of customers who left without being served.
     */
    private int noOfCustLeft;
    /**
     * Total waiting time of customers.
     */
    private double waitingTime;

    /**
     * Create and initalize a statistic object.
     */
    Statistics() {
        this.noOfCustServed = 0;
        this.noOfCustLeft = 0;
        this.waitingTime = 0;
    }

    /**
     * Increment the number of customers served.
     */
    void custServed() {
        this.noOfCustServed++;
    }

    /**
     * Increment the number of customers who left without being served.
     */
    void custLeft() {
        this.noOfCustLeft++;
    }

    /**
     * Increment of the waiting time of customer and the number of customers served.
     * 
     * @param time Waiting time.
     */
    void addWaitingTime(double time) {
        this.waitingTime += time;
        this.noOfCustServed++;
    }

    /**
     * Calculating the average waiting time of customer through dividing waiting
     * time by total number of customer served.
     * 
     * @return Average waiting time.
     */
    double getAvgWaitingTime() {
        double avgWaitingTime = this.waitingTime / this.noOfCustServed;
        return Double.isNaN(avgWaitingTime) ? 0 : avgWaitingTime;
    }

    /**
     * Formats the Statistics to print all information gathered.
     * 
     * @return String representation of the statistics.
     */
    @Override
    public String toString() {
        return String.format("[%.3f %d %d]", this.getAvgWaitingTime(), 
            this.noOfCustServed, this.noOfCustLeft);
    }
}
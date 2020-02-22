import java.util.ArrayList;
import java.util.Scanner;
import cs2030.simulator.EventSimulator;

/**
 * The Main class for testing purposes.
 */
public class Main {
    /**
     * Creates a Simulator to process the inputs. This is done by using the Scanner
     * object to take inputs from the user. User has to enter the number of servers,
     * followed by the timing the customers are arriving. Set as arguments for
     * Simulator object.
     * 
     * @param args String[]
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int seed = sc.nextInt();
        int noOfServers = sc.nextInt();
        int noOfSelfCheckouts = sc.nextInt();
        int length = sc.nextInt();
        int noOfCustomers = sc.nextInt();
        double arrRate = sc.nextDouble();
        double svcRate = sc.nextDouble();
        double restRate = sc.nextDouble();
        double restProb = sc.nextDouble();
        double greedyProb = sc.nextDouble();
        sc.close();
        EventSimulator simulator = new EventSimulator(seed, noOfServers, noOfSelfCheckouts, length, 
            noOfCustomers, arrRate, svcRate, restRate, restProb, greedyProb);
        simulator.simulate();
    }
}

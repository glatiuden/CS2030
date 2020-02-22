package cs2030.simulator;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The Shop class maintains the list of servers and support queries for server.
 */
class Shop {
    /** List of servicers. */
    private final List<Servicer> servicers;
    /** Probability of a server takes a break. */
    private final double restProbability;

    /**
     * Create a new shop with a given number of servicer.
     * 
     * @param noOfServers       The number of servers.
     * @param noOfSelfCheckouts The number of self-checkout counters.
     * @param restProb          Probability of a server takes a break.
     * @param length            Maximum queue length of a server.
     */
    Shop(int noOfServers, int noOfSelfCheckouts, double restProb, int length) {
        List<Server> listOfServers = IntStream.rangeClosed(1, noOfServers)
                .mapToObj(i -> new Server(i))
                .collect(Collectors.toList());
        List<SelfCheckout> listOfSelfCheckouts = 
                IntStream.rangeClosed(1 + noOfServers, noOfServers + noOfSelfCheckouts)
                .mapToObj(i -> new SelfCheckout(i)).collect(Collectors.toList());
        this.servicers = new ArrayList<>();
        this.servicers.addAll(listOfServers);
        this.servicers.addAll(listOfSelfCheckouts);
        this.restProbability = restProb;
        Servicer.setMaxQueue(length);
    }

    /**
     * Find a server or self-checkout matching the given ID.
     *
     * @param id The ID of server or self-checkout to be searched.
     * @return Optional.empty if no server or self-checkout matches the given ID, or
     *         the optional of the server or self-checkout if a match is found.
     */
    Optional<? extends Servicer> find(int id) {
        return this.servicers.stream().filter(x -> x.getId() == id).findFirst();
    }

    /**
     * Find a server or self-checkout to serve a customer.
     *
     * @param arrTime A double representing arrival time of the Customer.
     * @return Optional.empty if all the server or self-checkout are occupied, or
     *         the optional of the server or self-checkout if a match is found.
     */
    Optional<? extends Servicer> findToServe(double arrTime) {
        return this.servicers.stream()
            .filter(s -> arrTime >= s.getNextServeTime() && s.isIdle())
            .findFirst();
    }

    /**
     * Find a server or self-checkout to serve a greedy customer.
     *
     * @return Optional.empty if all the servicers are full, or the
     *         optional of the server or self-checkout who have waiting capacity.
     */
    Optional<? extends Servicer> findToWait() {
        return this.servicers.stream().filter(Servicer::hasWaitingSlot).findFirst();
    }

    /**
     * Find a server or self-checkout to serve a greedy customer.
     *
     * @return Optional.empty if all the servicers are full, or the
     *         optional of the server or self-checkout with the shortest queue.
     */
    Optional<? extends Servicer> findForGreedyCust() {
        return this.servicers.stream().filter(Servicer::hasWaitingSlot)
                .min(Comparator.comparing(Servicer::getQueueLength));
    }

    /**
     * Gets the probability of a server resting.
     * 
     * @return A double representing the probability of a server resting.
     */
    double getRestProbability() {
        return this.restProbability;
    }
}

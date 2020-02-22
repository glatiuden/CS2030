import java.util.List;
import java.util.function.Predicate;
import java.util.stream.*;
import java.util.Optional;

/**
 * A shop object maintains the list of servers and support queries for server.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Shop {
  /** List of servers. */
  public final List<Server> servers;

  /**
   * Create a new shop with a given number of servers.
   * 
   * @param numOfServers The number of servers.
   */
  Shop(int numOfServers) {
    this.servers = IntStream.rangeClosed(1, numOfServers).mapToObj(Server::new).collect(Collectors.toList());
  }

  Shop(List<Server> servers) {
    this.servers = servers;
  }

  public Optional<Server> find(Predicate<Server> predicate) {
    return this.servers.stream().filter(predicate).findFirst();
  }

  public Shop replace(Server server) {
    return new Shop(this.servers.stream().map(o -> o.toString().equals(server.toString()) ? server : o)
        .collect(Collectors.toList()));
  }

  /**
   * Return a string representation of this shop.
   * 
   * @return A string reprensetation of this shop.
   */
  public String toString() {
    return servers.toString();
  }
}

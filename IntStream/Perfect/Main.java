import java.util.Arrays;
import java.util.stream.IntStream;

public class Main
{
    static boolean isPerfect(int n)
    {
        // return IntStream.rangeClosed(2, n / 2).reduce(1, (sum, test) -> n % test == 0 ? sum + test : sum) == n;
        return n == IntStream.rangeClosed(1, n / 2)
        .filter(i -> n % i == 0)
        .sum();
    }   
}
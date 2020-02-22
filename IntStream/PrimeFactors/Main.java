import java.util.stream.IntStream;

public class Main
{
    static IntStream factors(int n) {
        return IntStream.range(1, n + 1).filter(i -> n % i == 0);
    }

    static IntStream primeFactors(int n) {
        return IntStream.range(1, n + 1).filter(i -> n % i == 0).filter(x -> isPrime(x));
    }

    static boolean isPrime(int n) {
        if (n == 1)
            return false;
        else
            return IntStream.range(2, n).noneMatch(x -> n % x == 0);
    }
}
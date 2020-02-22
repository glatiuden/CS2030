import java.util.stream.IntStream;

class Main
{
    public static boolean isSquare(int n)
    {
        return IntStream.rangeClosed(0, n).map(x -> x * x).anyMatch(x -> x == n);
    }
}
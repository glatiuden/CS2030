import java.util.stream.IntStream;
import java.util.OptionalDouble;

public class Main {
    public static OptionalDouble variance(int[] numbers) {
        if (numbers.length <= 1) {
            return OptionalDouble.empty();
        } else {
            double average = IntStream.of(numbers).average().getAsDouble();
            double result = IntStream.of(numbers).mapToDouble(x -> (x - average) * (x - average)).sum()
                    / (numbers.length - 1);
            return OptionalDouble.of(result);
        }
    }
}
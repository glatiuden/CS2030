import java.util.stream.*;
import java.util.Collections;
import java.util.Arrays;

class Main {
    static long countRepeats(int[] array) {
        return IntStream.range(1, array.length).filter(i -> i - 2 < 0 || array[i - 2] != array[i])
                .filter(i -> array[i] == array[i - 1]).count();
        // return Arrays.stream(array).filter(i -> Collections.frequency(Arrays.asList(array), i) >1).distinct().count();
    }
}
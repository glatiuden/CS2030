import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class ChildTrace<T> extends Trace<T>
{
    private ChildTrace(T trace, ArrayList<T> records) {
        super(trace, records);
    }

    @SafeVarargs
    public static <T> ChildTrace<T> of(T value, T... args) {
        ArrayList<T> newHistory = new ArrayList<T>();

        for (int i = 0; i < args.length; i++) {
            newHistory.add(args[i]);
        }
        newHistory.add(value);

        return new ChildTrace<T>(value, newHistory);
    }
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Trace<T> {
    private final T trace;
    private final ArrayList<T> records;

    protected Trace(T trace, ArrayList<T> records) {
        this.trace = trace;
        this.records = records;
    }

    @SafeVarargs
    public static <T> Trace<T> of(T value, T... args) {
        ArrayList<T> newHistory = new ArrayList<T>();
        for (int i = 0; i < args.length; i++) {
            newHistory.add(args[i]);
        }
        newHistory.add(value);
        return new Trace<T>(value, newHistory);
    }

    public T get() {
        return this.trace;
    }

    public ArrayList<T> history() {
        return new ArrayList<T>(this.records);
    }

    public Trace<T> back(int index) {
        int newIndex = 0;
        if (index > this.records.size())
            newIndex = 1;
        else
            newIndex = this.records.size() - index;
        ArrayList<T> newList = new ArrayList<T>(this.records.subList(0, newIndex));
        T newValue = newList.get(newIndex - 1);
        return new Trace<T>(newValue, newList);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
         if (obj instanceof Trace) {
            Trace<T> compareTrace = (Trace<T>) obj;
            return this.history().equals(compareTrace.history());
        } else {
            return false;
        }
    }

    public Trace<T> map(Function<? super T, ? extends T> f) {
        ArrayList<T> newList = new ArrayList<T>(this.records);
        T trace = f.apply(this.trace);
        newList.add(trace);
        return new Trace<T>(trace, newList);
    }

    public Trace<T> flatMap(Function<? super T, ? extends Trace<? extends T>> function) 
    {
        ArrayList<T> newList = new ArrayList<T>(this.records);
        Trace<? extends T> trace = function.apply(this.trace); 
        ArrayList<? extends T> traceHistory = trace.history();
        int k = traceHistory.size();
        for(int i = 0; i < k; i++)
        {
           if(!newList.contains(traceHistory.get(i)))
                newList.add(traceHistory.get(i));
        }
        return new Trace<T>(trace.get(), newList);
    }

    @Override
    public String toString() {
        return records.toString();
    }
}

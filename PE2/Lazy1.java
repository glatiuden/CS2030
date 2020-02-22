import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Function;

class Lazy<T> {
    private Optional<T> value;
    private Supplier<T> supValue;

    Lazy(T value) {
        this.value = Optional.of(value);
    }

    Lazy(Supplier<T> supValue) {
        this.value = Optional.empty();
        this.supValue = supValue;
    }

    Lazy(T value, Supplier<T> supValue) {
        this.value = Optional.ofNullable(value);
        this.supValue = supValue;
    }

    private void setValue(T value) {
        this.value = Optional.of(value);
    }

    private void setSupValue(Supplier<T> supValue) {
        this.supValue = supValue;
    }

    static <T> Lazy<T> of(T v) {
        return new Lazy<T>(v);
    }

    static <T> Lazy<T> of(Supplier<T> s) {
        return new Lazy<T>(s);
    }

    T get() {
        return value.map(Function.identity()).orElseGet(() -> {
            T newValue = supValue.get();
            setValue(newValue);
            return newValue;
        });
    }

    public String toString() {
        return value.map(x -> x.toString()).orElse("?");
    }
}

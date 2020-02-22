import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.List;

class Lazy<T> {
    Optional<T> value;
    Supplier<T> supValue;

    Lazy(T value) {
        this.value = Optional.of(value);
    }

    Lazy(Optional<T> value) {
        this.value = value;
    }

    Lazy(Supplier<T> supValue) {
        this.value = Optional.empty();
        this.supValue = supValue;
    }

    Lazy(T value, Supplier<T> supValue) {
        this.value = Optional.ofNullable(value);
        this.supValue = supValue;
    }

    void setValue(T value) {
        this.value = Optional.ofNullable(value);
    }

    void setSupValue(Supplier<T> supValue) {
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

    <T> Supplier<T> memoize(Supplier<T> factory) {
        final List<T> cache = new ArrayList<>();
        return () -> {
            if (cache.isEmpty()) {
                cache.add(factory.get());
            }
            return cache.get(0);
        };
    }

    <U> Lazy<U> map(Function<T, U> functor) {
        Supplier<U> s = () -> functor.apply(get());
        return new Lazy<U>(memoize(s));
    }

    Lazy<T> flatMap(Function<T, Lazy<T>> monad) {
        Lazy<T> newLazy = monad.apply(get());
        return new Lazy<T>(memoize(() -> newLazy.get()));
    }

    // Lazy<T> combine(Lazy<T> u, BiFunction<T, T, T> accumulator) {
    // return new Lazy<T>(() -> accumulator.apply(this.get(), u.get()));
    // }

    @SuppressWarnings("unchecked")
    <T, U, R> Lazy<R> combine(Lazy<U> u, BiFunction<T, U, R> accumulator) {
        return new Lazy<R>(memoize(() -> accumulator.apply((T) this.get(), u.get())));
    }

    public <U> Lazy<U> reduce(Lazy<U> identity, BiFunction<? super U, ? super T, ? extends U> accumulator) {
        return new Lazy<U>(() -> accumulator.apply(identity.get(), (T) this.get()));
    }

    Lazy<Boolean> test(Predicate<T> pred) {
        return new Lazy<Boolean>(memoize(() -> pred.test(get())));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Lazy) {
            Lazy newObj = (Lazy) obj;
            return this.get().equals(newObj.get());
        }
        return false;
    }

    Lazy<Integer> compareTo(Lazy<T> obj) {
        T thisValue = this.get();
        T objValue = obj.get();
        if (thisValue instanceof Integer && objValue instanceof Integer) {
            return new Lazy<Integer>(
                    () -> Integer.valueOf(thisValue.toString()).compareTo(Integer.valueOf(objValue.toString())));
        } else {
            return new Lazy<Integer>(() -> 0);
        }
    }

    @Override
    public String toString() {
        return value.map(x -> x.toString()).orElse("?");
    }
}

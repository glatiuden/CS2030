import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.List;

//extends Comparable<? super T>
class Lazy<T extends Comparable<T>> {
    Optional<T> value;
    Supplier<T> supValue;

    Lazy(T value) {
        this.value = Optional.ofNullable(value);
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

    static <T extends Comparable<T>> Lazy<T> of(T v) {
        return new Lazy<T>(v);
    }

    static <T extends Comparable<T>> Lazy<T> of(Supplier<T> s) {
        return new Lazy<T>(s);
    }

    T get() {
        return value.map(Function.identity()).orElseGet(() -> {
            T newValue = supValue.get();
            setValue(newValue);
            return newValue;
        });
    }

    <T extends Comparable<T>> Supplier<T> memoize(Supplier<T> factory) {
        final List<T> cache = new ArrayList<>();
        return () -> {
            if (cache.isEmpty()) {
                cache.add(factory.get());
            }
            return cache.get(0);
        };
    }

    <U extends Comparable<U>> Lazy<U> map(Function<T, U> functor) {
        Supplier<U> s = () -> functor.apply(get());
        return new Lazy<U>(memoize(s));
    }

    <U extends Comparable<U>> Lazy<U> flatMap(Function<T, Lazy<U>> monad) {
        Lazy<U> newLazy = monad.apply(get());
        return new Lazy<U>(memoize(() -> newLazy.get()));
    }

    // Lazy<T> combine(Lazy<T> u, BiFunction<T, T, T> accumulator) {
    // return new Lazy<T>(() -> accumulator.apply(this.get(), u.get()));
    // }

    // <U extends Comparable<U>> Lazy<U> combine(Lazy<T> identity, BiFunction<?
    // super T, ? super T, U> accumulator) {
    // return new Lazy<U>(() -> accumulator.apply(this.get(), identity.get()));
    // }

    <U extends Comparable<U>, R extends Comparable<R>> Lazy<R> combine(Lazy<U> u,
            BiFunction<? super T, ? super U, ? extends R> accumulator) {
        return new Lazy<R>(memoize(() -> accumulator.apply(this.get(), u.get())));
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
        return new Lazy<Integer>(() -> this.get().compareTo(obj.get()));
    }

    @Override
    public String toString() {
        return value.map(x -> x.toString()).orElse("?");
    }
}

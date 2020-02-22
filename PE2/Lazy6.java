import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.List;

class Lazy<T extends Comparable<T>> {
    class CacheSupplier<T> implements Supplier<T> {
        private Optional<T> cachedValue;
        private Supplier<T> cacheSupplier;

        CacheSupplier(Supplier<T> cacheSupplier) {
            this.cachedValue = Optional.empty();
            this.cacheSupplier = cacheSupplier;
        }

        @Override
        public T get() {
            return cachedValue.map(Function.identity()).orElseGet(() -> {
                T cachedSupplierValue = cacheSupplier.get();
                this.cachedValue = Optional.ofNullable(cachedSupplierValue);
                return cachedSupplierValue;
            });
        }
    }

    Optional<T> value;
    CacheSupplier<T> supValue;

    Lazy(T value) {
        this.value = Optional.ofNullable(value);
    }

    Lazy(Supplier<T> supValue) {
        this.value = Optional.empty();
        this.supValue = new CacheSupplier<T>(supValue);
    }

    void setValue(T value) {
        this.value = Optional.ofNullable(value);
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

    <U extends Comparable<U>> Lazy<U> map(Function<T, U> functor) {
        Supplier<U> s = () -> functor.apply(get());
        return new Lazy<U>(s);
    }

    <U extends Comparable<U>> Lazy<U> flatMap(Function<T, Lazy<U>> monad) {
        Lazy<U> newLazy = monad.apply(get());
        return new Lazy<U>(() -> newLazy.get());
    }

    <U extends Comparable<U>, R extends Comparable<R>> Lazy<R> combine(Lazy<U> u, BiFunction<T, U, R> accumulator) {
        return new Lazy<R>(() -> accumulator.apply(this.get(), u.get()));
    }

    Lazy<Boolean> test(Predicate<T> pred) {
        return new Lazy<Boolean>(() -> pred.test(get()));
    }

    Lazy<Integer> compareTo(Lazy<T> obj) {
        return new Lazy<Integer>(() -> this.get().compareTo(obj.get()));
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

    @Override
    public String toString() {
        return value.map(x -> x.toString()).orElse("?");
    }
}

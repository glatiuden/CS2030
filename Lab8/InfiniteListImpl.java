package cs2030.mystream;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class InfiniteListImpl<T> implements InfiniteList<T> {
    class CacheSupplier<T> implements Supplier<T> {
        private Optional<T> cachedValue = Optional.empty();
        private Supplier<T> cacheSupplier;
        public CacheSupplier(Supplier<T> cacheSupplier) {
            this.cacheSupplier = cacheSupplier;
        }
        @Override
        public T get() {
            if (cachedValue.isEmpty()) {
                cachedValue = Optional.ofNullable(cacheSupplier.get());
            }
            return cachedValue.get();
        }
    }
    private final CacheSupplier<Optional<T>> head;
    private final CacheSupplier<InfiniteListImpl<T>> tail;

    protected InfiniteListImpl(Supplier<Optional<T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = new CacheSupplier<>(head);
        this.tail = new CacheSupplier<>(tail);
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> s) {
        return new InfiniteListImpl<T>(() -> Optional.of(s.get()), (() -> InfiniteListImpl.generate(s)));
    };

    public static <T> InfiniteListImpl<T> iterate(T seed, Function<? super T, ? extends T> next) {
        return new InfiniteListImpl<T>(() -> Optional.of(seed), () -> InfiniteListImpl.iterate(next.apply(seed), next));
    }

    public InfiniteListImpl<T> get() {
        head.get().ifPresent(System.out::println);
        return tail.get();
    }

    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        return new InfiniteListImpl<R>(() -> {
            return head.get().map(mapper);
        }, () -> {
            InfiniteListImpl<T> currTail = tail.get();
            return currTail.isEmptyList() ? new EmptyList<R>() : currTail.map(mapper);
        });
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        return new InfiniteListImpl<T>(() -> {
            return head.get().filter(predicate);
        }, () -> {
            InfiniteListImpl<T> currTail = tail.get();
            return currTail.isEmptyList() ? currTail : currTail.filter(predicate);
        });
    }

    public InfiniteListImpl<T> limit(long n) {
        if (n <= 0) {
            return new EmptyList<T>();
        } else if (n == 1) {
            return new InfiniteListImpl<T>(head, () -> {
                return head.get().isPresent() ? new EmptyList<T>() : tail.get().limit(tail.get().isEmptyList() ? 0 : 1);
            });
        } else {
            return new InfiniteListImpl<T>(head, () -> {
                InfiniteListImpl<T> currTail = tail.get();
                return currTail.isEmptyList() ? currTail : currTail.limit(n - (head.get().isPresent() ? 1 : 0));
            });
        }
    }

    public Object[] toArray() {
        InfiniteListImpl<T> currentList = this;
        List<T> list = new ArrayList<>();
        while (!currentList.isEmptyList()) {
            currentList.head.get().ifPresent(list::add);
            currentList = currentList.tail.get();
        }
        return list.toArray();
    }

    public long count() {
        int count = 0;
        InfiniteListImpl<T> currentList = this;
        while (!currentList.isEmptyList()) {
            if (currentList.head.get().isPresent())
                count++;
            currentList = currentList.tail.get();
        }
        return count;
    }

    public void forEach(Consumer<? super T> action) {
        InfiniteListImpl<T> currentList = this;
        while (!currentList.isEmptyList()) {
            currentList.head.get().ifPresent(action::accept);
            currentList = currentList.tail.get();
        }
    }

    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        InfiniteListImpl<T> currentList = this;
        Optional<T> result = Optional.empty();
        boolean first = true;
        while (!currentList.isEmptyList()) {
            Optional<T> currHead = currentList.head.get();
            if (currHead.isPresent()) {
                if (first) {
                    first = false;
                    result = currHead;
                } else {
                    result = Optional.of(accumulator.apply(result.get(), currHead.get()));
                }
            }
            currentList = currentList.tail.get();
        }
        return result;
    }

    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator) {
        U result = identity;
        InfiniteListImpl<T> currentList = this;
        while (!currentList.isEmptyList()) {
            Optional<T> currHead = currentList.head.get();
            if (currHead.isPresent()) {
                result = accumulator.apply(result, currHead.get());
            }
            currentList = currentList.tail.get();
        }
        return result;
    }

    public InfiniteListImpl<T> takeWhile(Predicate<? super T> predicate) {
        InfiniteListImpl<T> currentList = this;
        if (currentList.isEmptyList())
            return new EmptyList<>();
        else {
            return new InfiniteListImpl<T>(() -> Optional.empty(), () -> {
                Optional<T> currHead = currentList.head.get();
                if (currHead.isPresent()) {
                    return predicate.test(currHead.get()) ? new InfiniteListImpl<T>(head, () -> tail.get().takeWhile(predicate)) : new EmptyList<>();
                } else {
                    return tail.get().takeWhile(predicate);
                }
            });
        }
    }

    public boolean isEmptyList() {
        return false;
    }
    
    public InfiniteListImpl<T> interleave(InfiniteListImpl<T> list1, InfiniteListImpl<T> list2)
    {
        return new InfiniteListImpl<>(() -> list1.get(), () -> interleave(list2,list1));
    }
}
package cs2030.mystream;

import java.util.Optional;

class EmptyList<T> extends InfiniteListImpl<T> {
    protected EmptyList() {
        super(() -> Optional.empty(), () -> new EmptyList<T>());
    }

    @Override
    public boolean isEmptyList() {
        return true;
    }
}
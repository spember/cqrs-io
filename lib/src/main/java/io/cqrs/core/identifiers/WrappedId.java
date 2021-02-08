package io.cqrs.core.identifiers;

import javax.annotation.Nonnull;
import java.util.Objects;

abstract class WrappedId<T> {

    protected final T value;


    public WrappedId(@Nonnull final T value) {
        this.value = value;
    }

    @Nonnull
    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WrappedId)) return false;
        WrappedId<?> wrappedId = (WrappedId<?>) o;
        return Objects.equals(value, wrappedId.value) && o.getClass() == this.getClass();
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

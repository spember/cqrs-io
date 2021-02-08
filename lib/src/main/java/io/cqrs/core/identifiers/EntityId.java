package io.cqrs.core.identifiers;

import javax.annotation.Nonnull;

/**
 * A wrapper class to enforce a concrete type for an Entity
 *
 * @param <T>
 */
public class EntityId<T> extends WrappedId<T> {
    public EntityId(@Nonnull final T value) {
        super(value);
    }
}

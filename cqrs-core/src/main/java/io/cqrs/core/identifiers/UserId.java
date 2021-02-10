package io.cqrs.core.identifiers;

import io.cqrs.core.Command;
import io.cqrs.core.event.Event;

import javax.annotation.Nonnull;

/**
 * Identifier of the User responsible for an {@link Event}, or the User initiating the change via a {@link Command}
 */
public class UserId<T> extends WrappedId<T> {
    public UserId(@Nonnull final T value) {
        super(value);
    }
}

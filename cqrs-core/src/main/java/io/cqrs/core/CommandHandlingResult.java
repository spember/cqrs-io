package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandHandlingResult<E extends Entity<? extends EntityId<?>>> {

    private Exception capturedError;

    // todo make this parameterized
    private E capturedEntityRoot;

    private final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> uncommittedEvents = new ArrayList<>();

    public CommandHandlingResult(@Nonnull final Exception capturedError) {
        this.capturedError = capturedError;
    }

    public CommandHandlingResult(
            @Nonnull final E entity,
            @Nonnull final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> events
    ) {
        this.capturedEntityRoot = entity;
        uncommittedEvents.addAll(events);
    }

    @Nonnull
    public Optional<Exception> maybeError() {
        return Optional.ofNullable(capturedError);
    }

    @Nonnull
    public Optional<E> maybeEntityRoot() {
        return Optional.ofNullable(capturedEntityRoot);
    }

    @Nonnull
    public List<EventEnvelope<? extends Event, ? extends EntityId<?>>> getUncommittedEvents() {
        return uncommittedEvents;
    }
}

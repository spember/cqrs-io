package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandHandlingResult {

    private Exception capturedError;

    // todo make this parameterized
    private Entity<EntityId<?>> capturedEntity;

    private final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> uncommittedEvents = new ArrayList<>();

    public CommandHandlingResult(@Nonnull final Exception capturedError) {
        this.capturedError = capturedError;
    }

    public CommandHandlingResult(
            @Nonnull final Entity<EntityId<?>> entity,
            @Nonnull final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> events
    ) {
        this.capturedEntity = entity;
        uncommittedEvents.addAll(events);
    }

    @Nonnull
    public Optional<Exception> maybeError() {
        return Optional.ofNullable(capturedError);
    }

    @Nonnull
    public Optional<? extends Entity<? extends EntityId<?>>> maybeEntity() {
        return Optional.ofNullable(capturedEntity);
    }

    @Nonnull
    public List<EventEnvelope<? extends Event, ? extends EntityId<?>>> getUncommittedEvents() {
        return uncommittedEvents;
    }
}

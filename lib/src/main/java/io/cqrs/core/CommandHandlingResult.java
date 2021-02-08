package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.identifiers.EntityId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandHandlingResult {

    private Exception capturedError;

    private final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> uncommittedEvents = new ArrayList<>();

    public CommandHandlingResult(final Exception capturedError) {
        this.capturedError = capturedError;
    }

    public CommandHandlingResult(final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> events) {
        uncommittedEvents.addAll(events);
    }

    Optional<Exception> maybeError() {
        return Optional.ofNullable(capturedError);
    }

    public List<EventEnvelope<? extends Event, ? extends EntityId<?>>> getUncommittedEvents() {
        return uncommittedEvents;
    }
}

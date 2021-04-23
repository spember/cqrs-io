package io.cqrs.core;

import io.cqrs.core.event.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A data structure which can be used as the return value from mutating an Aggregate.
 *
 */
public class AggregateMutationResult {
    /*
    Typically, you're going to return Events on success and an error on failure,
    and as a personal stylistic approach I don't like throwing exceptions for accounted for
    errors, and prefer to react and handle them as part of normal control flow, rather than
    raising exceptions.
     */

    private final List<Event> uncommittedEvents = new ArrayList<>();
    private Exception capturedError;

    public AggregateMutationResult(final List<Event> uncommittedEvents) {
        this.uncommittedEvents.addAll(uncommittedEvents);
    }

    public AggregateMutationResult(final Event... uncommittedEvents) {
        this.uncommittedEvents.addAll(Arrays.asList(uncommittedEvents));
    }

    public AggregateMutationResult(final Exception capturedError) {
        this.capturedError = capturedError;
    }

    public AggregateMutationResult addEvent(Event event) {
        uncommittedEvents.add(event);
        return this;
    }

    public Optional<Exception> maybeError() {
        return Optional.ofNullable(capturedError);
    }

    public List<? extends Event> getUncommittedEvents() {
        return uncommittedEvents;
    }
}

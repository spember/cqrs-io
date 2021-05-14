package io.cqrs.core.process;

import io.cqrs.core.Aggregate;
import io.cqrs.core.CqrsCommand;
import io.cqrs.core.CqrsEntity;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.event.EventFactory;
import io.cqrs.core.identifiers.EntityId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A data structure used to signal to Callers the result of an Aggregate mutation, either
 *
 */
public class AggregateMutationResult<C extends CqrsCommand<?>, A extends Aggregate<? extends EntityId<?>>> {

    // have this accept the command, and use that to generate the event factory internally
    // responsible for a a main Aggregate BUT ALSO tracks event changes internally as a map of entity to event
    // grabbing the uncommitted events returns a collapsed list of all, regardless of entity.
    // this makes actually saving of the events very efficient, as we can do it one single write, in our append-only
    // journal

    /*
    Typically, you're going to return Events on success and an error on failure,
    and as a personal stylistic approach I don't like throwing exceptions for accounted for
    errors, and prefer to react to them as part of normal control flow, rather than
    raising exceptions.
     */


    /**
     * AMR.for(agg) //static to
     * .error()
     * // or
     * // ..addEventsFor(
     */
//    private final List<Event> uncommittedEvents = new ArrayList<>();
    private Exception capturedError;
    private A capturedAggregate;
    private C sourceCommand;

    private final Map<EntityId<?>, EventFactory<C, ?>> eventStorage = new HashMap<>();

    public AggregateMutationResult(final A capturedAggregate, final C sourceCommand) {
        this.capturedAggregate = capturedAggregate;
        this.sourceCommand = sourceCommand;
    }

    public AggregateMutationResult<C, A> error(Exception exception) {
        this.capturedError = exception;
        return this;
        // todo: break this up into success and failure classes so that we cannot add events after an error.
    }

    public <E extends CqrsEntity<?>> AggregateMutationResult<C, A> addEvents(E entity, Event... events) {
        if (!eventStorage.containsKey(entity.getId())) {
            eventStorage.put(entity.getId(), new EventFactory<>(sourceCommand, entity));
        }
        for (final Event event : events) {
            eventStorage.get(entity.getId()).addNext(event);
        }
        return this;
    }

    public Optional<Exception> maybeError() {
        return Optional.ofNullable(capturedError);
    }

    public List<EventEnvelope<? extends Event, ? extends EntityId<?>>> getUncommittedEventEnvelopes() {
        if (eventStorage.size() == 0) {
            return new ArrayList<>(); // if empty, return empty
        } else if (eventStorage.size() == 1) {
            return eventStorage.values().stream().findFirst().get().getEventEnvelopes();
        } else {
            return eventStorage.values().stream()
                .flatMap(cEventFactory -> cEventFactory.getEventEnvelopes().stream())
                .collect(Collectors.toList());
        }
    }

    public A getCapturedAggregate() {
        return capturedAggregate;
    }
}

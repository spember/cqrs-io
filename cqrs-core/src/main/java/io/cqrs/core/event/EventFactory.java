package io.cqrs.core.event;

import io.cqrs.core.CqrsCommand;
import io.cqrs.core.identifiers.UserId;
import io.cqrs.core.process.CommandHandlingResult;
import io.cqrs.core.CqrsEntity;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to streamline the generation of new Events during the handling of a Command. Completely optional
 * but is intended to be useful in cutting down on boiler plate code.
 */
public class EventFactory<C extends CqrsCommand<? extends UserId<?>>, E extends CqrsEntity<? extends EntityId<?>,?>> {
    // todo: implements 'eventEnvelopeStore' in order to merge multiple later?
    private final E entity;
    private final C sourceCommand;
    private final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> eventEnvelopes = new ArrayList<>();

    public EventFactory(@Nonnull final C sourceCommand, @Nonnull final E entity) {
        this.entity = entity;
        this.sourceCommand = sourceCommand;
    }

    /**
     * Add an event to the current uncommitted batch and apply it to the Entity.
     * Will with the creation of the EventCoreData and Event Envelope for you.
     *
     * @param nextEvent The next {@link Event} in the sequence
     * @return this Factory
     */
    @Nonnull
    public EventFactory<C, E> addNext(@Nonnull Event nextEvent) {
        EventEnvelope<? extends Event, ? extends EntityId<?>> envelope = new EventEnvelope<>(
                nextEvent,
                new EventCoreData<>(
                        entity.getId(),
                        entity.getRevision()+1,
                        Instant.now(),
                        sourceCommand.getTimeOccurred(),
                        sourceCommand.getUserId().toString()
                        )
        );
        entity.apply(envelope);
        eventEnvelopes.add(envelope);
        return this;
    }

    @Nonnull
    public List<EventEnvelope<? extends Event, ? extends EntityId<?>>> getEventEnvelopes() {
        return eventEnvelopes;
    }
}

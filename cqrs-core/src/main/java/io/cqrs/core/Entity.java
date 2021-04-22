package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.exceptions.EventsOutOfOrderException;
import io.cqrs.core.exceptions.IncorrectTargetException;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

/**
 * Suggest class to extend. The Entity provides basic functionality that each entity needs to help with ensuring
 * proper Event ordering during application
 *
 * @param <EI>
 */
public abstract class Entity<EI extends EntityId<?>> implements CqrsEntity<EI> {

    protected EI id;
    protected int revision = 0;

    public Entity(@Nonnull final EI id) {
        this.id = id;
    }

    @Nonnull
    @Override
    public EI getId() {
        return id;
    }

    @Override
    public int getRevision() {
        return revision;
    }

    @Override
    public boolean isBare() {
        return revision == 0;
    }

    @Nonnull
    @Override
    public CqrsEntity<EI> apply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) throws EventsOutOfOrderException {
        if (!envelope.getEventCoreData().getEntityId().equals(this.id)) {
            throw new IncorrectTargetException("Attempted to apply an event meant for " +
                    (envelope.getEventCoreData().getEntityId()) + " to this entity, " + this.id);
        }
        if (envelope.getEventCoreData().getRevision() != this.revision+1) {
            throw new EventsOutOfOrderException("Expecting revision " + (this.revision+1) +" but instead received " +
                    envelope.getEventCoreData().getRevision());
        }
        revision = envelope.getEventCoreData().getRevision();
        handleEventApply(envelope);
        return this;
    }

    abstract protected void handleEventApply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope);
}

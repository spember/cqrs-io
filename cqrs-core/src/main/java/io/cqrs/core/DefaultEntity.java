package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventApplier;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.exceptions.EventsOutOfOrderException;
import io.cqrs.core.exceptions.IncorrectTargetException;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public abstract class DefaultEntity<EI extends EntityId> implements Entity<EI>{

    protected EI id = null;
    protected int revision = 0;

    public DefaultEntity(@Nonnull final EI id) {
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

    @Override
    public Entity<EI> apply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) throws EventsOutOfOrderException {
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

package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.exceptions.EventsOutOfOrderException;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

public interface Entity<EI extends EntityId> {

    @Nonnull
    EI getId();

    int getRevision();

    boolean isBare();


   Entity<EI> apply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) throws EventsOutOfOrderException;
}

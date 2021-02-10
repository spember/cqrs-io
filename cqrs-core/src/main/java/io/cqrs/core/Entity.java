package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.exceptions.EventsOutOfOrderException;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

/**
 * An Entity represents some object which has a lifecycle. It grows and changes over time. It is important and these
 * state changes are key to your system.
 *
 * @param <EI>
 */
public interface Entity<EI extends EntityId> {

    @Nonnull
    EI getId();

    int getRevision();

    /**
     * An Entity is 'bare' if either a) it has no events applied to it yet (e.g. the code has just created the object).
     * This is used to signify that an Entity can be loaded (e.g. to current state) or if it is brand new during the
     * creation process.
     *
     * @return true if 'bare', false otherwise
     */
    boolean isBare();

    @Nonnull
    Entity<EI> apply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) throws EventsOutOfOrderException;
}

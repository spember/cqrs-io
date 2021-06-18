package io.cqrs.core;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.exceptions.EventsOutOfOrderException;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

/**
 * A CqrsEntity represents some object which has a lifecycle. It grows and changes over time. These
 * state changes are key to your system.
 *
 * While the interface is provided, we suggest using the abstract {@link Entity}
 *
 * @param <EI>
 */
public interface CqrsEntity<EI extends EntityId<?>> {

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
    CqrsEntity<EI> apply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) throws EventsOutOfOrderException;
}

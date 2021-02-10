package io.cqrs.core.event;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.List;


/**
 *
 */
public interface EventRepository {

    @Nonnull
    <T, EI extends EntityId<T>> List<EventEnvelope<? extends Event, ? extends EntityId<?>>> listAllForEntity(@Nonnull EI entityId);

    void write(@Nonnull List<EventEnvelope<? extends Event, ? extends EntityId<?>>> events);

}

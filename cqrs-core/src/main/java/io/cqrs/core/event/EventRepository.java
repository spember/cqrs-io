package io.cqrs.core.event;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.List;


/**
 *
 */
public interface EventRepository {
    @Nonnull
    List<EventEnvelope<? extends Event, ? extends EntityId<?>>> listAllByIds(@Nonnull EntityId<?>... entityIds);

    void write(@Nonnull List<EventEnvelope<? extends Event, ? extends EntityId<?>>> events);
}

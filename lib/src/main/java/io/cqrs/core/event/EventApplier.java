package io.cqrs.core.event;

import io.cqrs.core.identifiers.EntityId;

import java.util.function.Consumer;

public interface EventApplier<E extends Event, EI extends EntityId> extends Consumer<EventEnvelope<E, EI>> {

}

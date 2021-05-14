package io.cqrs.core;

import io.cqrs.core.event.EventRepository;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

public abstract class Aggregate<EI extends EntityId<?>> extends Entity<EI> implements CqrsAggregate {
    public Aggregate(@Nonnull final EI id) {
        super(id);
    }

    @Nonnull
    @Override
    public Aggregate<EI> loadCurrentState(final EventRepository eventRepository) {
        eventRepository.listAllByIds(this.getId())
                .forEach(this::apply);
        return this;
    }
}

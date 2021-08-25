package io.cqrs.core;

import io.cqrs.core.event.EventRepository;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

public abstract class Aggregate<EI extends EntityId<?>, SELF extends Aggregate<EI, SELF>> extends Entity<EI, SELF> implements CqrsAggregate<SELF> {

    public Aggregate(@Nonnull final EI id) {
        super(id);
    }

    @Nonnull
    @Override
    public SELF loadCurrentState(@Nonnull final EventRepository eventRepository) {
        eventRepository
                .listAllByIds(this.getId())
                .forEach(this::apply);
        return this.self();
    }
}

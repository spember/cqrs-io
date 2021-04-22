package io.cqrs.core;

import io.cqrs.core.event.EventRepository;

import javax.annotation.Nonnull;

/**
 * An Aggregate is the boundary in which multiple Entities operate as one unit. One Entity is the 'root', who's id is
 * the only reference allowed by other Entities / Aggregates.
 *
 * An Aggregate contains business logic to ensure that it (and it's members) can never be in an inconsistent state.
 * Instead of typical Getters and Setters, it should have meaningful, descriptive, method names that return failures or
 * Events that need to be persisted.
 *
 */
public interface Aggregate {

    @Nonnull
    Aggregate loadCurrentState(EventRepository eventRepository);

}

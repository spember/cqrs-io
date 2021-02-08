package io.cqrs.core;

/**
 * An Aggregate is the boundary in which multiple Entities operate as one unit. One Entity is the 'root', who's id is
 * the only reference allowed by other Entities / Aggregates.
 *
 * An Aggregate accepts Commands, and returns failures or Events that need to be Persisted.
 *
 * An Aggregate in practice is a hybrid service class and domain object
 */
public interface Aggregate {

    Aggregate loadCurrentState();

    CommandHandlingResult handle(Command command);
}

package io.cqrs.core.exceptions;

/**
 * Thrown when an event is applied to the correct Entity, but the incorrect instance of the Entity. In other words,
 * the entity id of the event does not match the entity it's being applied to.
 * The target is wrong!
 */
public class IncorrectTargetException extends RuntimeException {
    public IncorrectTargetException() {
        super();
    }

    public IncorrectTargetException(final String message) {
        super(message);
    }
}

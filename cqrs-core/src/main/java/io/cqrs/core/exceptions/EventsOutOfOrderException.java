package io.cqrs.core.exceptions;

import javax.annotation.Nonnull;

public class EventsOutOfOrderException extends RuntimeException {
    public EventsOutOfOrderException() {
        super();
    }

    public EventsOutOfOrderException(@Nonnull final String message) {
        super(message);
    }
}

package io.cqrs.core.furniture.sofa.events;

import io.cqrs.core.event.Event;

public class NameUpdated implements Event {
    private final String nextName;

    public NameUpdated(final String nextName) {
        this.nextName = nextName;
    }

    public String getNextName() {
        return nextName;
    }
}

package io.cqrs.core.furniture.sofa.events;

import io.cqrs.core.event.Event;

public class SomeoneSat implements Event {

    private final int times;

    public SomeoneSat(final int times) {
        this.times = times;
    }

    public int getTimes() {
        return times;
    }
}

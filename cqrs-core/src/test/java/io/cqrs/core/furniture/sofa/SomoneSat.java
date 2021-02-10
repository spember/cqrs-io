package io.cqrs.core.furniture.sofa;

import io.cqrs.core.event.Event;

public class SomoneSat implements Event {

    private final int times;

    public SomoneSat(final int times) {
        this.times = times;
    }

    public int getTimes() {
        return times;
    }
}

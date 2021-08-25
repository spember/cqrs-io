package io.cqrs.core.furniture.sofa.events;

import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventAlias;

// adding an event alias for registry testing purposes; the alias takes precedence over the class name, which is useful
// in situations where you rename a class file after the event has hit production
@EventAlias(alias = "someone_sat")
public class SomeoneSat implements Event {

    private final int times;

    public SomeoneSat(final int times) {
        this.times = times;
    }

    public int getTimes() {
        return times;
    }
}

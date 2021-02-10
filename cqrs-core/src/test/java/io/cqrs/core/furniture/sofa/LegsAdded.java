package io.cqrs.core.furniture.sofa;

import io.cqrs.core.event.Event;

public class LegsAdded implements Event {

    private final int count;

    public LegsAdded(final int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}

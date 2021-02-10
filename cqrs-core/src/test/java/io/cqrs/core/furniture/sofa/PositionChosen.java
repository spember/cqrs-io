package io.cqrs.core.furniture.sofa;

import io.cqrs.core.event.Event;

public class PositionChosen implements Event {
    private final Cushion.Position position;

    public PositionChosen(final Cushion.Position position) {
        this.position = position;
    }

    public Cushion.Position getPosition() {
        return position;
    }
}

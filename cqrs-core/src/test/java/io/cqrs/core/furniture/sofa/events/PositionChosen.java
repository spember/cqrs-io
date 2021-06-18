package io.cqrs.core.furniture.sofa.events;

import io.cqrs.core.event.Event;
import io.cqrs.core.furniture.sofa.Cushion;

public class PositionChosen implements Event {
    private final Cushion.Position position;

    public PositionChosen(final Cushion.Position position) {
        this.position = position;
    }

    public Cushion.Position getPosition() {
        return position;
    }
}

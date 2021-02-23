package io.cqrs.core.furniture.sofa;

import io.cqrs.core.DefaultEntity;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventApplier;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.Map;

public class Cushion extends DefaultEntity<CushionId> {

    private Position position = Position.NOT_CHOSEN;
    private int timesSatOn = 0;

    public Cushion(@Nonnull final CushionId id) {
        super(id);
    }

    public Position getPosition() {
        return position;
    }

    public int getTimesSatOn() {
        return timesSatOn;
    }

    @Override
    protected void handleEventApply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) {
        if (envelope.getEvent() instanceof PositionChosen) {
            position = ((EventEnvelope<PositionChosen, SofaId>) envelope).getEvent().getPosition();
        }
        if (envelope.getEvent() instanceof SomeoneSat) {
            timesSatOn += ((EventEnvelope<SomeoneSat, SofaId>) envelope).getEvent().getTimes();
        }
    }

    public enum Position {
        LEFT, RIGHT, MIDDLE, NOT_CHOSEN
    }
}

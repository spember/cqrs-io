package io.cqrs.core.furniture.sofa;

import io.cqrs.core.Entity;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.furniture.sofa.events.PositionChosen;
import io.cqrs.core.furniture.sofa.events.SomeoneSat;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

public class Cushion extends Entity<CushionId, Cushion> {

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
    protected void handleEventApply(@Nonnull EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) {
        if (envelope.getEvent() instanceof PositionChosen) {
            position = ((EventEnvelope<PositionChosen, SofaId>) envelope).getEvent().getPosition();
        }
        if (envelope.getEvent() instanceof SomeoneSat) {
            timesSatOn += ((EventEnvelope<SomeoneSat, SofaId>) envelope).getEvent().getTimes();
        }
    }

//    @Override
//    protected void handleEventApply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) {
//        if (envelope.getEvent() instanceof PositionChosen) {
//            position = ((EventEnvelope<PositionChosen, SofaId>) envelope).getEvent().getPosition();
//        }
//        if (envelope.getEvent() instanceof SomeoneSat) {
//            timesSatOn += ((EventEnvelope<SomeoneSat, SofaId>) envelope).getEvent().getTimes();
//        }
//    }

    public enum Position {
        LEFT, RIGHT, MIDDLE, NOT_CHOSEN
    }
}

package io.cqrs.core.furniture.sofa;

import io.cqrs.core.DefaultEntity;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventApplier;

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
    protected <E extends Event> Map<Class, EventApplier> getAppliers() {

        appliers.put(PositionChosen.class, (EventApplier<PositionChosen, CushionId>) positionEventEnvelope ->
            position = positionEventEnvelope.getEvent().getPosition()
        );

        appliers.put(SomoneSat.class, (EventApplier<SomoneSat, CushionId>) eventEnvelope ->
                timesSatOn += eventEnvelope.getEvent().getTimes()
        );

        return appliers;
    }

    public enum Position {
        LEFT, RIGHT, MIDDLE, NOT_CHOSEN
    }
}

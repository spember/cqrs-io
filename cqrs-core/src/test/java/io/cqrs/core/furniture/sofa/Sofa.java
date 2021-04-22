package io.cqrs.core.furniture.sofa;

import io.cqrs.core.Entity;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Sofa extends Entity<SofaId> {

    private int numLegs = 0;
    private int numSeats = 0;
    private String publicName = "";
    private final List<CushionId> cushionIds = new ArrayList<>();
    // we could put the cushions
    private final List<Cushion> cushions = new ArrayList<>();

    public Sofa(@Nonnull final SofaId id) {
        super(id);
    }

    @Override
    protected void handleEventApply(@Nonnull final EventEnvelope<? extends Event, ? extends EntityId<?>> envelope) {
        if (envelope.getEvent() instanceof LegsAdded) {
            applyLegsAdded((EventEnvelope<LegsAdded, SofaId>) envelope);
        } else if (envelope.getEvent() instanceof NameUpdated) {
            applyNameChange((EventEnvelope<NameUpdated, SofaId>) envelope);
        } else if (envelope.getEvent() instanceof SeatsAdded) {
            this.numSeats += 1;
        }
    }

    public void applyLegsAdded(EventEnvelope<LegsAdded, SofaId> envelope) {
        numLegs += envelope.getEvent().getCount();
    }

    public void applyNameChange(EventEnvelope<NameUpdated, SofaId> envelope) {
        this.publicName = envelope.getEvent().getNextName();
    }

    public int getNumLegs() {
        return numLegs;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public String getPublicName() {
        return publicName;
    }
}

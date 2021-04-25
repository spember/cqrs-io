package io.cqrs.core.furniture.sofa;

import io.cqrs.core.Aggregate;
import io.cqrs.core.process.AggregateMutationResult;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.event.EventRepository;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Sofa extends Aggregate<SofaId> {

    private int numLegs = 0;
    private int numSeats = 0;
    private String publicName = "";
    private final List<CushionId> cushionIds = new ArrayList<>();
    // we could put the cushions
    private final List<Cushion> cushions = new ArrayList<>();

    public Sofa(@Nonnull final SofaId id) {
        super(id);
    }

    @Nonnull
    @Override
    public Sofa loadCurrentState(final EventRepository eventRepository) {
        return (Sofa)super.loadCurrentState(eventRepository);
    }

    public AggregateMutationResult<Sofa> assemble(final String name, final int numLegs, final int numSeats) {
        if (!this.isBare()) {
            return new AggregateMutationResult<>(this, new RuntimeException("Cannot re-assemble a sofa"));
        }
        // check for legs and cushion ratios?
        AggregateMutationResult<Sofa> events = new AggregateMutationResult<>(this,
                new EntityCreated(),
                new NameUpdated(name),
                new LegsAdded(numLegs)
        );
        for (int i =0; i < numSeats; i++) {
            events.addEvent(new SeatsAdded());
        }
        return events;
    }

    public AggregateMutationResult<Sofa> addMoreLegs(final int legsToAdd) {
        if (this.isBare()) {
            return new AggregateMutationResult<>(this, new RuntimeException("Cannot be applied on a bare sofa"));
        }
        //  A trivial check: ensure that legs are not odd. The idea is to show that a command should be
        // evaluated such that it doesn't put the Entity into an invalid state
        if ((this.getNumLegs() + legsToAdd) % 2 == 1) {
            return new AggregateMutationResult<>(this, new RuntimeException("Cannot have an odd number of legs!"));
        }

        return new AggregateMutationResult<>(this, new LegsAdded(legsToAdd));
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

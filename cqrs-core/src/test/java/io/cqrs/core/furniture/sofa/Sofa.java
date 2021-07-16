package io.cqrs.core.furniture.sofa;

import io.cqrs.core.Aggregate;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;
import io.cqrs.core.furniture.sofa.events.LegsAdded;
import io.cqrs.core.furniture.sofa.events.NameUpdated;
import io.cqrs.core.furniture.sofa.events.SeatsAdded;
import io.cqrs.core.process.AggregateMutationResult;
import io.cqrs.core.event.Event;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Sofa extends Aggregate<SofaId, Sofa> {

    private int numLegs = 0;
    private int numSeats = 0;
    private String publicName = "";
    private final List<CushionId> cushionIds = new ArrayList<>();
    // we could put the cushions
    private final List<Cushion> cushions = new ArrayList<>();

    public Sofa(@Nonnull final SofaId id) {
        super(id);
    }

    public AggregateMutationResult<CreateNewSofa, Sofa> assemble(final CreateNewSofa command) {
        // what if this received a 'context' object that allowed for .. what?
        AggregateMutationResult<CreateNewSofa, Sofa> response = new AggregateMutationResult<>(this, command);

        if (!this.isBare()) {
            return response.error(new RuntimeException("Cannot re-assemble a sofa"));
        }
        // check for legs and cushion ratios?
        response.addEvents(this,
                new NameUpdated(command.getDescription()),
                new LegsAdded(command.getNumLegs())
        );


        for (int i =0; i < command.getNumSeats(); i++) {
            response.addEvents(this, new SeatsAdded());
        }

        return response;
    }

    public AggregateMutationResult<AddLegs, Sofa> addMoreLegs(final AddLegs sourceCommand) {
        if (this.isBare()) {
            return new AggregateMutationResult<>(this, sourceCommand).error(new RuntimeException("Unknown Sofa"));
        }
        //  A trivial check: ensure that legs are not odd. The idea is to show that a command should be
        // evaluated such that it doesn't put the Entity into an invalid state
        if ((this.getNumLegs() + sourceCommand.getRequestedLegs()) % 2 == 1) {
            return new AggregateMutationResult<>(this, sourceCommand)
                .error(new RuntimeException("Cannot have an odd number of legs!"));
        }
        return new AggregateMutationResult<>(this, sourceCommand)
            .addEvents(this,
            new LegsAdded(sourceCommand.getRequestedLegs()));
    }



    @Override
    @SuppressWarnings("unchecked")
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

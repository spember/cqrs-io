package io.cqrs.core.furniture.sofa;

import io.cqrs.core.AggregateRoot;
import io.cqrs.core.DefaultEntity;
import io.cqrs.core.event.EventApplier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sofa extends DefaultEntity<SofaId> implements AggregateRoot {

    private int numLegs = 0;
    private int numSeats = 0;
    private String publicName = "";
    private final List<CushionId> cushionIds = new ArrayList<>();
    // we could put the cushions
    private final List<Cushion> cushions = new ArrayList<>();

    public Sofa(@Nonnull final SofaId id) {
        super(id);
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

    @Override
    protected Map<Class, EventApplier> getAppliers() {
        appliers.put(LegsAdded.class, (EventApplier<LegsAdded, SofaId>) legsAddedSofaIdEventEnvelope ->
                numLegs += legsAddedSofaIdEventEnvelope.getEvent().getCount()
        );
        appliers.put(NameUpdated.class, (EventApplier<NameUpdated, SofaId>) eventEnvelope ->
                publicName = eventEnvelope.getEvent().getNextName()
        );
        appliers.put(SeatsAdded.class, (EventApplier<SeatsAdded, SofaId>) eventEnvelope ->
                numSeats += 1
        );


        return appliers;
    }
}

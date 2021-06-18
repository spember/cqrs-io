package io.cqrs.core.furniture.commands;

import io.cqrs.core.Command;
import io.cqrs.core.furniture.sofa.FurnitureMakerId;

import java.time.Instant;

public class CreateNewSofa extends Command<FurnitureMakerId> {
    private final int numSeats;
    private final int numLegs;
    private final String sku;
    private final String description;

    public CreateNewSofa(final FurnitureMakerId userId, final Instant timeOccurred, final int requestedSeats, final int requestedLegs, final String sku, final String description) {
        super(userId, timeOccurred);
        this.numSeats = requestedSeats;
        this.numLegs = requestedLegs;
        this.sku = sku;
        this.description = description;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getNumLegs() {
        return numLegs;
    }

    public String getSku() {
        return sku;
    }

    public String getDescription() {
        return description;
    }
}

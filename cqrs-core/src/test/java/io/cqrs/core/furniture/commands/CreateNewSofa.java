package io.cqrs.core.furniture.commands;

import io.cqrs.core.Command;
import io.cqrs.core.furniture.sofa.FurnitureMakerId;
import io.cqrs.core.identifiers.UserId;

import java.time.Instant;

public class CreateNewSofa extends Command<FurnitureMakerId> {
    private final int numSeats;
    private final int numLegs;

    public CreateNewSofa(final FurnitureMakerId userId, final Instant timeOccurred, final int requestedSeats, final int requestedLegs) {
        super(userId, timeOccurred);
        this.numSeats = requestedSeats;
        this.numLegs = requestedLegs;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getNumLegs() {
        return numLegs;
    }
}

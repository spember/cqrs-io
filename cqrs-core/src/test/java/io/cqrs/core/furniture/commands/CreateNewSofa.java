package io.cqrs.core.furniture.commands;

import io.cqrs.core.DefaultCommand;
import io.cqrs.core.identifiers.UserId;

import java.time.Instant;

public class CreateNewSofa<UI extends UserId<?>> extends DefaultCommand<UI> {
    private final int numSeats;
    private final int numLegs;

    public CreateNewSofa(final UI userId, final Instant timeOccurred, final int requestedSeats, final int requestedLegs) {
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

package io.cqrs.core.furniture.commands;

import io.cqrs.core.Command;
import io.cqrs.core.identifiers.UserId;

import java.time.Instant;

public class CreateNewSofa<UI extends UserId> implements Command<UI> {

    private final UI userId;
    private final Instant timeOccurred;

    private final int numSeats;
    private final int numLegs;

    public CreateNewSofa(final UI userId, final Instant timeOccurred, final int requestedSeats, final int requestedLegs) {
        this.userId = userId;
        this.timeOccurred = timeOccurred;
        this.numSeats = requestedSeats;
        this.numLegs = requestedLegs;
    }

    @Override
    public UI getUserId() {
        return userId;
    }

    @Override
    public Instant getTimeOccurred() {
        return timeOccurred;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public int getNumLegs() {
        return numLegs;
    }
}

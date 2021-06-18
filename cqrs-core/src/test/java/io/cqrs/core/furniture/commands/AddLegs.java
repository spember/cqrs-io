package io.cqrs.core.furniture.commands;

import io.cqrs.core.Command;
import io.cqrs.core.furniture.sofa.FurnitureMakerId;
import io.cqrs.core.identifiers.UserId;

import java.time.Instant;

public class AddLegs extends Command<FurnitureMakerId> {

    private final int requestedLegs;

    public AddLegs(final FurnitureMakerId userId, final Instant timeOccurred, final int requestedLegs) {
        super(userId, timeOccurred);
        this.requestedLegs = requestedLegs;
    }

    public int getRequestedLegs() {
        return requestedLegs;
    }
}

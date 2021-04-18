package io.cqrs.core.furniture.commands;

import io.cqrs.core.DefaultCommand;
import io.cqrs.core.identifiers.UserId;

import java.time.Instant;

public class AddLegs<UI extends UserId<?>> extends DefaultCommand<UI> {

    private final int requestedLegs;

    public AddLegs(final UI userId, final Instant timeOccurred, final int requestedLegs) {
        super(userId, timeOccurred);
        this.requestedLegs = requestedLegs;
    }

    public int getRequestedLegs() {
        return requestedLegs;
    }
}

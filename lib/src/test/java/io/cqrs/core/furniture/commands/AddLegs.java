package io.cqrs.core.furniture.commands;

import io.cqrs.core.Command;
import io.cqrs.core.identifiers.UserId;

import java.time.Instant;

public class AddLegs<UI extends UserId> implements Command<UI> {
    private final UI userId;
    private final Instant timeOccurred;

    private final int requestedLegs;

    public AddLegs(final UI userId, final Instant timeOccurred, final int requestedLegs) {
        this.userId = userId;
        this.timeOccurred = timeOccurred;
        this.requestedLegs = requestedLegs;
    }

    @Override
    public UI getUserId() {
        return null;
    }

    @Override
    public Instant getTimeOccurred() {
        return null;
    }

    public int getRequestedLegs() {
        return requestedLegs;
    }
}

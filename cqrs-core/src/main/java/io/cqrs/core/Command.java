package io.cqrs.core;

import io.cqrs.core.identifiers.UserId;

import javax.annotation.Nonnull;
import java.time.Instant;

public abstract class Command<UI extends UserId<?>> implements CqrsCommand<UI> {

    private final UI userId;
    private final Instant timeOccurred;

    public Command(@Nonnull final UI userId, @Nonnull final Instant timeOccurred) {
        this.userId = userId;
        this.timeOccurred = timeOccurred;
    }

    @Nonnull
    @Override
    public UI getUserId() {
        return userId;
    }

    @Nonnull
    @Override
    public Instant getTimeOccurred() {
        return timeOccurred;
    }
}

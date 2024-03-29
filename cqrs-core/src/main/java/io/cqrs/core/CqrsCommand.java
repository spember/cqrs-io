package io.cqrs.core;

import io.cqrs.core.identifiers.UserId;

import javax.annotation.Nonnull;
import java.time.Instant;

public interface CqrsCommand<UI extends UserId<?>> {
    @Nonnull
    UI getUserId();

    @Nonnull
    Instant getTimeOccurred();
}

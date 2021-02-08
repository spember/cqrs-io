package io.cqrs.core;

import io.cqrs.core.identifiers.UserId;

import java.time.Instant;

public interface Command<UI extends UserId> {

    UI getUserId();
    Instant getTimeOccurred();


}

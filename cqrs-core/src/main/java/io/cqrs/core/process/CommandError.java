package io.cqrs.core.process;

import javax.annotation.Nonnull;

public class CommandError<SUCCESS> extends CommandHandlingResult<SUCCESS> {

    public CommandError(@Nonnull Exception exception) {
        this.capturedError = exception;
    }
}

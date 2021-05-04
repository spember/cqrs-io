package io.cqrs.core.process;

public class CommandSuccess<SUCCESS> extends CommandHandlingResult<SUCCESS> {

    public CommandSuccess(SUCCESS payload) {
        this.successPayload = payload;
    }
}

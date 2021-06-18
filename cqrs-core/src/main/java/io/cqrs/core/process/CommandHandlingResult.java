package io.cqrs.core.process;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class CommandHandlingResult<SUCCESS> {

    protected Exception capturedError;
    protected SUCCESS successPayload;

    public void with(
        @Nonnull Consumer<SUCCESS> onSuccess,
        @Nonnull Consumer<Exception> onError) {

        if (capturedError != null) {
            onError.accept(capturedError);
        } else if(successPayload != null) {
            onSuccess.accept(successPayload);
        } else {
            onError.accept(new RuntimeException("No error or success passed to the result"));
        }
    }


    @Nonnull
    public Optional<Exception> maybeError() {
        return Optional.ofNullable(capturedError);
    }

    public Optional<SUCCESS> maybePayload() {
        return Optional.ofNullable(successPayload);
    }

}

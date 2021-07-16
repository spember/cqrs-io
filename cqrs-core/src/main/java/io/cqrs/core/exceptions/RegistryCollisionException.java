package io.cqrs.core.exceptions;

public class RegistryCollisionException extends RuntimeException {

    public RegistryCollisionException() {
        super();
    }

    public RegistryCollisionException(String message) {
        super(message);
    }
}

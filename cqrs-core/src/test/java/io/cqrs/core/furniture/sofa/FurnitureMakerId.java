package io.cqrs.core.furniture.sofa;

import io.cqrs.core.identifiers.UserId;

import javax.annotation.Nonnull;

public class FurnitureMakerId extends UserId<String> {
    public FurnitureMakerId(@Nonnull final String value) {
        super(value);
    }
}

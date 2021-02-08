package io.cqrs.core.furniture.sofa;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

public class SofaId extends EntityId<String> {
    public SofaId(@Nonnull final String value) {
        super(value);
    }
}

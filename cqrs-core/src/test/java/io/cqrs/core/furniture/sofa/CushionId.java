package io.cqrs.core.furniture.sofa;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.UUID;

public class CushionId extends EntityId<UUID> {
    public CushionId(@Nonnull final UUID value) {
        super(value);
    }
}

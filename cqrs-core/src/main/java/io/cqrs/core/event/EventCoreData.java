package io.cqrs.core.event;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.time.Instant;

/**
 * Contains the fields that are common to all Events.
 *
 * The goal with this class is to extract all common, repeated fields such that developers only need to worry about
 * the 'core' fields for their Event data central to their domain.
 *
 * The core data is less rarely used during Event application, but should still be available in certain situations
 * (e.g. you have a 'lastUpdated' field or other timestamps on your entity)
 *
 * Note that while the EntityId is a fixed type for the class, the 'createdBy' field is flexible in that the event
 * may be the result of different User Types
 */
public class EventCoreData<EI extends EntityId> {
    private final EI entityId;
    private final int revision;
    private final Instant instantObserved;
    private final Instant instantOccurred;
    private final String createdBy;

    public EventCoreData(@Nonnull final EI entityId, final int revision, @Nonnull final Instant instantObserved,
                         @Nonnull final Instant instantOccurred, @Nonnull final String createdBy) {
        this.entityId = entityId;
        this.revision = revision;
        this.instantObserved = instantObserved;
        this.instantOccurred = instantOccurred;
        this.createdBy = createdBy;
    }

    public EventCoreData(@Nonnull final EI entityId, final int revision, @Nonnull final Instant instantObserved,
                         @Nonnull final String createdBy) {
        this(entityId, revision, instantObserved, instantObserved, createdBy);
    }

    public EventCoreData(@Nonnull final EI entityId, final int revision, @Nonnull final String createdBy) {
        this(entityId, revision, Instant.now(), Instant.now(), createdBy);
    }

    @Nonnull
    public EI getEntityId() {
        return entityId;
    }

    public int getRevision() {
        return revision;
    }

    @Nonnull
    public Instant getInstantObserved() {
        return instantObserved;
    }

    @Nonnull
    public Instant getInstantOccurred() {
        return instantOccurred;
    }

    @Nonnull
    public String getCreatedBy() {
        return createdBy;
    }
}

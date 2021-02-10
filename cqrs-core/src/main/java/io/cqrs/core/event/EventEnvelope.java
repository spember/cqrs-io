package io.cqrs.core.event;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;

public class EventEnvelope<V extends Event, EI extends EntityId> {

    private final V event;
    private final EventCoreData<EI> eventCoreData;

    public EventEnvelope(@Nonnull final V event,
                         @Nonnull final EventCoreData<EI> eventCoreData) {
        this.event = event;
        this.eventCoreData = eventCoreData;
    }

    @Nonnull
    public V getEvent() {
        return event;
    }

    @Nonnull
    public EventCoreData<EI> getEventCoreData() {
        return eventCoreData;
    }
}

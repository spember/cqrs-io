package io.cqrs.core.event;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryEventRepository implements EventRepository {

    private List<EventEnvelope<? extends Event, ? extends EntityId<?>>> eventStore = new ArrayList<>();


    @Nonnull
    @Override
    public List<EventEnvelope<? extends Event, ? extends EntityId<?>>> listAllByIds(@Nonnull final EntityId<?>... entityIds) {
        List<EntityId<?>> ids = Arrays.asList(entityIds);
        return eventStore.stream()
            .filter(eventEnvelope -> ids.contains(eventEnvelope.getEventCoreData().getEntityId()))
            .collect(Collectors.toList());
    }

    @Override
    public void write(@Nonnull final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> events) {
        eventStore.addAll(events);
    }
}

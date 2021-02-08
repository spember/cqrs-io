package io.cqrs.core.event;

import io.cqrs.core.identifiers.EntityId;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryEventRepository implements EventRepository {

    private List<EventEnvelope<? extends Event, ? extends EntityId<?>>> eventStore = new ArrayList<>();

    @Nonnull
    @Override
    public <T, EI extends EntityId<T>> List<EventEnvelope<? extends Event, ? extends EntityId<?>>> listAllForEntity(@Nonnull final EI entityId) {
        return eventStore.stream()
                .filter(eventEnvelope -> eventEnvelope.getEventCoreData().getEntityId().equals(entityId))
                .collect(Collectors.toList());
    }

    @Override
    public void write(@Nonnull final List<EventEnvelope<? extends Event, ? extends EntityId<?>>> events) {
        eventStore.addAll(events);
    }


//    @Nonnull
//    @Override
//    public <E extends Event, EI extends EntityId> List<EventEnvelope<E, EI>> listAllForEntity(@Nonnull final EI entityId) {
//        return (List<EventEnvelope<E, EI>>) eventStore.stream()
//                .filter(eventEnvelope -> eventEnvelope.getEventCoreData().getEntityId().equals(entityId));
//    }
//
//    @Override
//    public <V extends Event, EI extends EntityId> void write(@Nonnull final List<EventEnvelope<V, EI>> events) {
//        eventStore.addAll(events);
//    }


}

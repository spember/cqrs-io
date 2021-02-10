package io.cqrs.core;

import io.cqrs.core.event.EventCoreData;
import io.cqrs.core.event.EventEnvelope;
import io.cqrs.core.event.EventRepositoryResolver;
import io.cqrs.core.exceptions.EventsOutOfOrderException;
import io.cqrs.core.exceptions.IncorrectTargetException;
import io.cqrs.core.furniture.sofa.Cushion;
import io.cqrs.core.furniture.sofa.CushionId;
import io.cqrs.core.furniture.sofa.LegsAdded;
import io.cqrs.core.furniture.sofa.PositionChosen;
import io.cqrs.core.furniture.sofa.Sofa;
import io.cqrs.core.furniture.sofa.SofaId;
import io.cqrs.core.furniture.sofa.SomoneSat;
import io.cqrs.core.identifiers.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(EventRepositoryResolver.class)
public class SingleEntityEventApplicationTest {

    @Test
    void eventsOutOfOrder() {
        SofaId id = new SofaId("mark2");
        Sofa sofa = new Sofa(id);
        EventsOutOfOrderException e = assertThrows(EventsOutOfOrderException.class, () ->
                sofa.apply(new EventEnvelope<>(new LegsAdded(10),
                        new EventCoreData<>(id, 2, new UserId<>("me")))));
        assertThat(e.getMessage()).isEqualTo("Expecting revision 1 but instead received 2");
    }

    @Test
    void testWrongId() {
        Sofa sofa = new Sofa(new SofaId("BestSofa"));
        assertThrows(IncorrectTargetException.class, () ->
                sofa.apply(new EventEnvelope<>(new LegsAdded(10),
                        new EventCoreData<>(new SofaId("BestSofa2"), 1, new UserId<>("me"))))
                );

    }

    @Test
    void testSofaApply() {
        SofaId id = new SofaId("mark1");
        Sofa sofa = new Sofa(id);
        sofa.apply(new EventEnvelope<>(new LegsAdded(2), new EventCoreData<>(id, 1, Instant.now(), Instant.now(), new UserId<>("me"))));
        assertThat(sofa.getNumLegs()).isEqualTo(2);
        assertThat(sofa.getRevision()).isEqualTo(1);
        // double-checking with the new Id("mark1") that the ids are equal and will not throw IncorrectTarget
        sofa.apply(new EventEnvelope<>(new LegsAdded(10), new EventCoreData<>(new SofaId("mark1"), 2, new UserId<>("me"))));
        assertThat(sofa.getNumLegs()).isEqualTo(12);
        assertThat(sofa.getRevision()).isEqualTo(2);
    }

    @Test
    void testCushionApply() {
        UserId userId = new UserId<>("me");
        CushionId id = new CushionId(UUID.randomUUID());
        Cushion cushion = new Cushion(id);

        cushion.apply(new EventEnvelope<>(new PositionChosen(Cushion.Position.LEFT), new EventCoreData<>(id, 1, userId)));
        cushion.apply(new EventEnvelope<>(new PositionChosen(Cushion.Position.MIDDLE), new EventCoreData<>(id, 2, userId)));
        cushion.apply(new EventEnvelope<>(new SomoneSat(1), new EventCoreData<>(id, 3, userId)));
        cushion.apply(new EventEnvelope<>(new SomoneSat(3), new EventCoreData<>(id, 4, userId)));

        assertThat(cushion.getPosition()).isEqualTo(Cushion.Position.MIDDLE);
        assertThat(cushion.getTimesSatOn()).isEqualTo(4);
    }

//    @Test
//    void testEventWrites(EventRepository eventRepository) {
//        List<EventEnvelope>
//        eventRepository.write();
//    }
}

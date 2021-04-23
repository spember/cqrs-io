package io.cqrs.core;

import io.cqrs.core.event.EventRepository;
import io.cqrs.core.event.EventRepositoryResolver;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;
import io.cqrs.core.furniture.sofa.FurnitureMakerId;
import io.cqrs.core.furniture.sofa.Sofa;
import io.cqrs.core.furniture.sofa.SofaAggregate;
import io.cqrs.core.furniture.sofa.SofaBoundaryService;
import io.cqrs.core.furniture.sofa.SofaId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.google.common.truth.Truth.assertThat;

/**
 * Test to exercise the full 'stack' at the service level.
 * Use this as a general demonstration of the patterns. At least, in Java.
 */
@ExtendWith(EventRepositoryResolver.class)
public class GeneralServiceLifecycleTest {

    private final FurnitureMakerId me = new FurnitureMakerId("tester@test.com");

    @Test
    void testCreate(EventRepository eventRepository) {
        SofaBoundaryService service = new SofaBoundaryService(eventRepository);
        CommandHandlingResult<Sofa> result = service.createNewSofa(
                new CreateNewSofa(
                me,
                Instant.now().minus(1, ChronoUnit.DAYS),
                3,
                6,
                        "sofa-1",
                        "My blue sofa")
        );

        assertThat(result.maybeError().isPresent()).isFalse();
        assertThat(result.maybeEntityRoot().isPresent()).isTrue();
        Sofa check = result.maybeEntityRoot().get();
        assertThat(check.getNumLegs()).isEqualTo(6);
        assertThat(check.getPublicName()).isEqualTo("My blue sofa");
    }

    @Test
    void testCreationAndReloading(EventRepository eventRepository) {
        SofaBoundaryService service = new SofaBoundaryService(eventRepository);

        //SofaId redSofa = new SofaId("red-2");

        CommandHandlingResult<Sofa> result = service.createNewSofa(
                new CreateNewSofa(
                        me,
                        Instant.now().minus(1, ChronoUnit.DAYS),
                        2,
                        4,
                        "red-2", "Big Red Sofa"));
        service.addLegsToSofa(new SofaId("red-2"), new AddLegs(me, Instant.now(), 4));

        Sofa check = new Sofa(new SofaId("red-2"));

        assertThat(check.getRevision()).isEqualTo(0);
        check.loadCurrentState(eventRepository);
        assertThat(check.getRevision()).isEqualTo(6);
        assertThat(check.getNumLegs()).isEqualTo(8);
        assertThat(check.getNumSeats()).isEqualTo(2);
    }
}

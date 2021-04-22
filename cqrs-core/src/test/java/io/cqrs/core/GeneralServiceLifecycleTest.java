package io.cqrs.core;

import io.cqrs.core.event.EventRepository;
import io.cqrs.core.event.EventRepositoryResolver;
import io.cqrs.core.furniture.commands.AddLegs;
import io.cqrs.core.furniture.commands.CreateNewSofa;
import io.cqrs.core.furniture.sofa.FurnitureMakerId;
import io.cqrs.core.furniture.sofa.SofaAggregate;
import io.cqrs.core.furniture.sofa.SofaId;
import io.cqrs.core.identifiers.UserId;
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
    void testCreate() {
        SofaAggregate sofaAggregate = new SofaAggregate(new SofaId("red-1"));
        CommandHandlingResult result = sofaAggregate.createNewSofa(
                new CreateNewSofa(
                me,
                Instant.now().minus(1, ChronoUnit.DAYS),
                3,
                6
                ));

        assertThat(result.maybeError().isPresent()).isFalse();
        assertThat(result.getUncommittedEvents().size()).isGreaterThan(0);
    }

    @Test
    void testCreationAndReloading(EventRepository eventRepository) {
        SofaId redSofa = new SofaId("red-2");
        SofaAggregate sofaAggregate = new SofaAggregate(redSofa);
        CommandHandlingResult result = sofaAggregate.createNewSofa(
                new CreateNewSofa(
                        me,
                        Instant.now().minus(1, ChronoUnit.DAYS),
                        2,
                        4
                ));
        eventRepository.write(result.getUncommittedEvents());

        eventRepository.write(
                sofaAggregate.addLegs(new AddLegs(me, Instant.now(), 4)).getUncommittedEvents()
        );

        SofaAggregate check = new SofaAggregate(redSofa);
        assertThat(check.getRoot().getRevision()).isEqualTo(0);

        check.loadCurrentState(eventRepository);
        assertThat(check.getRoot().getRevision()).isEqualTo(5);
        assertThat(check.getRoot().getNumLegs()).isEqualTo(8);
        assertThat(check.getRoot().getNumSeats()).isEqualTo(2);
    }
}

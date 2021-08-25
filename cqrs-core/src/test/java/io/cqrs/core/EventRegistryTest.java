package io.cqrs.core;

import io.cqrs.core.event.EventRegistry;
import io.cqrs.core.exceptions.RegistryCollisionException;
import io.cqrs.core.failure.FailureEvent1;
import io.cqrs.core.furniture.sofa.events.LegsAdded;
import io.cqrs.core.furniture.sofa.events.SomeoneSat;
import io.cqrs.core.resolvers.EventRegistryResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reflections.ReflectionsException;


import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(EventRegistryResolver.class)
public class EventRegistryTest {

    @Test
    public void sampleScan(EventRegistry registry) {
        assertThat(registry.aliasForEventClass(LegsAdded.class).get()).isEqualTo("LegsAdded");
        assertThat(registry.classFromAlias("LegsAdded").get()).isEqualTo(LegsAdded.class);
    }

    @Test
    public void testThatAliasWorks(EventRegistry registry) {
        assertThat(registry.aliasForEventClass(SomeoneSat.class).get()).isEqualTo("someone_sat");
        assertThat(registry.classFromAlias("someone_sat").get()).isEqualTo(SomeoneSat.class);
    }

    @Test
    public void missingAliasesShouldBeEmpty(EventRegistry registry) {
        assertThat(registry.classFromAlias("test").isPresent()).isFalse();
        assertThat(registry.aliasForEventClass(FailureEvent1.class).isPresent()).isFalse();
    }

    @Test
    public void collidingAliasesShouldThrow() {
        assertThrows(RegistryCollisionException.class, () -> new EventRegistry().scan("io.cqrs.core.failure"));
    }

    @Test
    public void scanningAnInvalidPackageShouldThrow() {
        // oops typo in package name
        assertThrows(ReflectionsException.class, () -> new EventRegistry().scan("io.cqrs.corf.furniture"));
    }
}

package io.cqrs.core;

import com.google.common.truth.Truth;
import io.cqrs.core.event.EventRegistry;
import io.cqrs.core.furniture.sofa.events.LegsAdded;
import io.cqrs.core.furniture.sofa.events.SomeoneSat;
import io.cqrs.core.resolvers.EventRegistryResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import static com.google.common.truth.Truth.assertThat;

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
    }



    // test error
    // test aliasing an event
}

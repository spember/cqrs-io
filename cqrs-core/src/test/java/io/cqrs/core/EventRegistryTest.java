package io.cqrs.core;

import io.cqrs.core.event.EventRegistry;
import io.cqrs.core.furniture.sofa.events.LegsAdded;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class EventRegistryTest {

    @Test
    public void sampleScan() {
        EventRegistry registry = new EventRegistry();
        try {
            registry.scan("io.cqrs.core.furniture");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(registry.aliasForEventClass(LegsAdded.class).get()).isEqualTo("LegsAdded");
        assertThat("One").isEqualTo("One");

    }
}

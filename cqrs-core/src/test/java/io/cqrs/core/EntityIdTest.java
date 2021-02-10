package io.cqrs.core;

import io.cqrs.core.furniture.sofa.SofaId;
import io.cqrs.core.identifiers.EntityId;
import io.cqrs.core.identifiers.UserId;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;

public class EntityIdTest {

    @Test
    void idCompareTest() {
        assertThat(new IntId(10)).isEqualTo(new IntId(10));
        assertThat(new IntId(10)).isNotEqualTo(new IntId(25));

        assertThat(new StrId("Foo")).isEqualTo(new StrId("Foo"));
        assertThat(new StrId("Foo")).isNotEqualTo(new StrId("test"));

        assertThat(new SofaId("BestSofa")).isEqualTo(new SofaId("BestSofa"));
    }

    @Test
    void userIdCompareTest() {
        assertThat(new UserId<>("test@test.com")).isEqualTo(new UserId<>("test@test.com"));
        assertThat(new UserId<>("test@test.com")).isNotEqualTo(new UserId<>("test2@test.com"));
        assertThat(new UserId<>("test@test.com")).isNotEqualTo(new UserId<>(UUID.randomUUID()));
    }

    @Test
    void crossIdTest() {
        assertThat(new UserId<>("123")).isNotEqualTo(new StrId("123"));
    }

    private class IntId extends EntityId<Integer> {

        public IntId(@Nonnull final Integer value) {
            super(value);
        }
    }

    private class StrId extends EntityId<String> {

        public StrId(@Nonnull final String value) {
            super(value);
        }
    }

}

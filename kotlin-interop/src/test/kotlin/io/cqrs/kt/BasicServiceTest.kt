package io.cqrs.kt

import io.cqrs.core.identifiers.UserId
import io.cqrs.core.event.EventCoreData
import io.cqrs.kt.aggregates.BookId
import org.junit.jupiter.api.Test
import java.time.Instant
import java.util.UUID

/**
 * A 'scratch' test to ensure the nonnull annotations work with kotlin, basically
 */
class BasicServiceTest {

    @Test
    fun `basic context test` () {
        val context = EventCoreData(BookId(UUID.randomUUID()), 1, Instant.now(),
            Instant.now(), UserId("test"))

    }
}

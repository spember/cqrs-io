package io.cqrs.kt

import com.google.common.truth.Truth.assertThat
import io.cqrs.core.identifiers.UserId
import io.cqrs.core.event.EventCoreData
import io.cqrs.kt.bookstore.core.identifiers.BookId
import org.junit.jupiter.api.Test
import java.time.Instant

/**
 * A 'scratch' test to ensure the nonnull annotations work with kotlin, basically
 */
class BasicServiceTest {

    @Test
    fun `basic context test` () {
        val context = EventCoreData(
            BookId("test-1"), 1, Instant.now(),
            Instant.now(), UserId("test"))
        assertThat(context.revision).isEqualTo(1)

    }
}

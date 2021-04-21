package io.cqrs.kt.librarymanager

import io.cqrs.core.event.EventRepository
import io.cqrs.kt.librarymanager.db.InMemoryEventRepository
import io.cqrs.kt.librarymanager.dto.LibraryResponse
import io.cqrs.kt.librarymanager.dto.PublishedBookResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

/**
 *
 */
@MicronautTest
class LibraryLifecycleTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var eventRepository: EventRepository

    @BeforeEach
    fun onEach() {
        (eventRepository as InMemoryEventRepository).reset()
    }

    @Test
    fun `test that the base library can be retrieved`() {
        val result:LibraryResponse? = client.toBlocking()
            .retrieve(HttpRequest.GET<LibraryResponse>("${server.uri}/library/library-1"), LibraryResponse::class.java)
        assertNotNull(result)
        assertEquals(result!!.archivedVolumes, 0)
        assertEquals(result.name, "The Testtown Regional Library")
    }

    @Test
    fun `test new book donations`() {
        val response: HttpResponse<Any> = client.toBlocking()
            .exchange(HttpRequest.POST("${server.uri}/library/library-1/donations",
                "{\"books\":[{\"isbn\":\"scary1\",\"copies\":10}]}"
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
        assertNotNull(response)

        val result:LibraryResponse? = client.toBlocking()
            .retrieve(HttpRequest.GET<LibraryResponse>("${server.uri}/library/library-1"), LibraryResponse::class.java)
        assertEquals(result!!.archivedVolumes, 10)

    }
}

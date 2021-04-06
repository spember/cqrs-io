package io.cqrs.kt.librarymanager

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import javax.inject.Inject

/**
 *
 */
@MicronautTest
class BookLifecycleEventTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Test
    fun `a book cannot be checked out twice`() {
        val response: Boolean = client.toBlocking()
            .retrieve(HttpRequest.GET<Boolean>("${server.uri}/reservations/checkout/123"), Boolean::class.java)
        assertTrue(response)
    }
}

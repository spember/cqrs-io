package io.cqrs.kt.bookstore

import io.cqrs.kt.bookstore.core.book.PublishedBook
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

/**
 * A quick test to exercise the PublishedBook value objects
 */
@MicronautTest
class PublishedBookLookupTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Test
    fun `test that everything is wired up`() {
        val response: PublishedBook? = client.toBlocking()
            .retrieve(HttpRequest.GET<PublishedBook>("${server.uri}/published-books"), PublishedBook::class.java)
        Assertions.assertNotNull(response)
        println("Received book was ${response!!.title}")
        Assertions.assertEquals(response.pageCount, 25)
    }
}

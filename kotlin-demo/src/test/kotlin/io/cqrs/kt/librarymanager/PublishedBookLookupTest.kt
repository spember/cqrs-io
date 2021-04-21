package io.cqrs.kt.librarymanager

import io.cqrs.kt.librarymanager.core.book.PublishedBook
import io.cqrs.kt.librarymanager.dto.PublishedBookResponse
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
        val response: PublishedBookResponse = client.toBlocking()
            .retrieve(HttpRequest.GET<PublishedBookResponse>("${server.uri}/published-books"), PublishedBookResponse::class.java)
        Assertions.assertNotNull(response)
        Assertions.assertEquals(response.books.first().pageCount, 25)
    }

    @Test
    fun `test find by isbn can hit` () {
        val ddd: PublishedBook? = client.toBlocking()
            .retrieve(HttpRequest.GET<PublishedBook>("${server.uri}/published-books/ddd4life"), PublishedBook::class.java)
        Assertions.assertNotNull(ddd)
        Assertions.assertEquals(ddd!!.pageCount, 450)
    }
}

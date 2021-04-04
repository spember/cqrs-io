package io.cqrs.kt.librarymanager

import com.fasterxml.jackson.core.type.TypeReference
import io.cqrs.kt.librarymanager.controllers.PublishedBookResponse
import io.cqrs.kt.librarymanager.core.book.PublishedBook
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
        println("Received book was ${response.books.first().title}")
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

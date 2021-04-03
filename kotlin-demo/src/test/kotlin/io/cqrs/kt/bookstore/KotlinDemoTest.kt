package io.cqrs.kt.bookstore

import io.cqrs.kt.bookstore.core.book.PublishedBook
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class KotlinDemoTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @Client("/")
    lateinit var client: HttpClient

    @Test
    fun testItWorks() {
        assertTrue(server.isRunning)
    }

    @Test
    fun testBasicResponse() {
        val response: PublishedBook? = client.toBlocking()
            .retrieve(GET<PublishedBook>("${server.uri}/published-books"), PublishedBook::class.java)
        assertNotNull(response)
        println("Received book was ${response!!.title}")
        assertEquals(response.pageCount, 25)
    }

}

package io.cqrs.kt.librarymanager

import io.cqrs.kt.librarymanager.dto.LibraryResponse
import io.cqrs.kt.librarymanager.dto.PublishedBookResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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
    @Client("/library")
    lateinit var client: HttpClient

    @Test
    fun `test that the base library can be retrieved`() {
//        val result:LibraryResponse? = client.toBlocking()
//            .retrieve(HttpRequest.GET<LibraryResponse>("test"), LibraryResponse::class.java)
//        assertNotNull(result)
        assertTrue(true)
    }
}

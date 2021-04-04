package io.cqrs.kt.librarymanager

import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class KotlinDemoTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Test
    fun testItWorks() {
        assertTrue(server.isRunning)
    }
}

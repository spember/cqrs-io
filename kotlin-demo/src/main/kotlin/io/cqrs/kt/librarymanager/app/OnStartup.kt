package io.cqrs.kt.librarymanager.app

import io.cqrs.kt.librarymanager.core.commands.FoundLibrary
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import io.cqrs.kt.librarymanager.core.identifiers.LibraryStaffId
import io.cqrs.kt.librarymanager.core.services.LibraryService
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.runtime.server.event.ServerStartupEvent
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 */
@Singleton
class OnStartup(@Inject private val libraryService: LibraryService): ApplicationEventListener<ServerStartupEvent> {

    override fun onApplicationEvent(event: ServerStartupEvent?) {
        log.info("Initializing Library...${libraryService}")
        val library = libraryService.handle(FoundLibrary(
            LibraryStaffId("bob"),
            LibraryId("library-1"),
            "The Testtown Regional Library",
            100

        ))
        log.info("Created Library ${library.libraryName}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(OnStartup::class.java)
    }
}

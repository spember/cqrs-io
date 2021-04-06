package io.cqrs.kt.librarymanager.core.services

import io.cqrs.core.event.EventRepository
import io.cqrs.kt.librarymanager.core.commands.FoundLibrary
import io.cqrs.kt.librarymanager.core.library.Library
import io.cqrs.kt.librarymanager.core.library.LibraryAggregate
import org.slf4j.LoggerFactory

/**
 *
 */
class LibraryService(private val eventRepository: EventRepository) {

    fun handle(command: FoundLibrary): Library {
        val libraryAggregate = LibraryAggregate(command.requestedId, eventRepository)
            .loadCurrentState()
        if (libraryAggregate.root.isBare) { // if it's bare that means we haven't created this library yet
            val result = libraryAggregate.handle(command)
            eventRepository.write(result.uncommittedEvents)
        }
        // should really return an error result here. perhaps expand the CommandHandlingResult?
        return libraryAggregate.root
    }

    companion object {
        private val log = LoggerFactory.getLogger(LibraryService::class.java)
    }
}

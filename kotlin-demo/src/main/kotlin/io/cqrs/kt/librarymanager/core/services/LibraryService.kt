package io.cqrs.kt.librarymanager.core.services

import io.cqrs.core.CommandHandlingResult
import io.cqrs.core.event.EventRepository
import io.cqrs.kt.librarymanager.core.commands.DonateBookToLibrary
import io.cqrs.kt.librarymanager.core.commands.FoundLibrary
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import io.cqrs.kt.librarymanager.core.library.Library
import io.cqrs.kt.librarymanager.core.library.LibraryAggregate
import org.slf4j.LoggerFactory
import java.util.function.Supplier

/**
 *
 */
class LibraryService(private val eventRepository: EventRepository) {

    fun handle(command: FoundLibrary): CommandHandlingResult = withResult {
            LibraryAggregate(command.requestedId, eventRepository)
                .handle(command)
        }

    fun handle(command: DonateBookToLibrary): CommandHandlingResult = withResult {
        LibraryAggregate(command.libraryId, eventRepository)
            .handle(command)
        }

    private fun withResult(supplier: Supplier<CommandHandlingResult>): CommandHandlingResult {
        val result = supplier.get()
        if (!result.maybeError().isPresent) {
            eventRepository.write(result.uncommittedEvents)
        }
        return result
    }

    companion object {
        private val log = LoggerFactory.getLogger(LibraryService::class.java)
    }
}

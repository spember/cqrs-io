package io.cqrs.kt.librarymanager.core.services

import io.cqrs.core.CommandHandlingResult
import io.cqrs.core.event.EventRepository
import io.cqrs.kt.librarymanager.core.book.PublishedBook
import io.cqrs.kt.librarymanager.core.book.PublishedBookRepository
import io.cqrs.kt.librarymanager.core.commands.DonateBooksToLibrary
import io.cqrs.kt.librarymanager.core.commands.FoundLibrary
import io.cqrs.kt.librarymanager.core.library.Library
import io.cqrs.kt.librarymanager.core.library.LibraryAggregate
import org.slf4j.LoggerFactory
import java.lang.RuntimeException
import java.util.function.Supplier

/**
 *
 */
class LibraryService(
    private val publishedBookRepository: PublishedBookRepository,
    private val eventRepository: EventRepository
    ) {

    fun handle(command: FoundLibrary): CommandHandlingResult<Library> = withResult {
            LibraryAggregate(command.requestedId).loadCurrentState(eventRepository)
                .handle(command)
        }

    fun handle(command: DonateBooksToLibrary): CommandHandlingResult<Library> = withResult {
        // check that command is valid... e.g. that the Books exist as Published books / valid isbns
        val bookLookup = command.books.mapNotNull {
            publishedBookRepository.findByIsbn(it.isbn)
        }
            .fold(mutableMapOf<String, PublishedBook>(), { acc, publishedBook ->
                acc[publishedBook.isbn] = publishedBook
                acc
            })
        // granted, the above does not cover repeats, nor does it call out 'which' isbns
        // may be missing
        if (bookLookup.keys.size != command.books.size) {
            log.error("Incoming request had ${command.books.size} isbns, but only ${bookLookup.keys.size} were found")
            CommandHandlingResult<Library>(RuntimeException("Invalid isbns present"))
        } else {
            LibraryAggregate(command.libraryId).loadCurrentState(eventRepository)
                .handle(command)
        }
        }


    private fun withResult(supplier: Supplier<CommandHandlingResult<Library>>): CommandHandlingResult<Library> {
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

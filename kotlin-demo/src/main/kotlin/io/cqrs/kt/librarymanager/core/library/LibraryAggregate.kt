package io.cqrs.kt.librarymanager.core.library

import io.cqrs.core.Aggregate
import io.cqrs.core.Command
import io.cqrs.core.CommandHandlingResult
import io.cqrs.core.event.EventFactory
import io.cqrs.core.event.EventRepository
import io.cqrs.core.identifiers.UserId
import io.cqrs.kt.librarymanager.core.commands.DonateBookToLibrary
import io.cqrs.kt.librarymanager.core.commands.FoundLibrary
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import java.lang.RuntimeException

/**
 *
 */
class LibraryAggregate(libraryId: LibraryId, private val eventRepository: EventRepository): Aggregate {

    // note that this library is meant to be fairly modular, in the sense that not every mechanism *needs* to be used
    // ... a loosely coupled framework. For example, one doesn't need to use this Aggregate concept - all of this
    // code could exist in the LibraryService - but we include it and use it here as an example of structure.

    val root: Library = Library(libraryId)

    init {
        loadCurrentState()
    }

    override fun loadCurrentState(): LibraryAggregate {
        eventRepository.listAllForEntity(this.root.id)
            .fold(root, { library, eventEnvelope ->
                library.apply(eventEnvelope) as Library
            })
        return this
    }

    override fun handle(command: Command<out UserId<*>>): CommandHandlingResult {
        return when (command) {
            is FoundLibrary -> handle(command)
            is DonateBookToLibrary -> handle(command)
            else -> CommandHandlingResult(RuntimeException("Unknown command received: ${command::class.java}"))
        }
    }

    private fun handle(command: FoundLibrary): CommandHandlingResult {
        return if (root.isBare) { // if it's bare that means we haven't created this library yet
            // Use the EventFactory to make creating new events easier.
            EventFactory<FoundLibrary, Library, LibraryId>(command, root)
                .addNext(LibraryFounded(command.requestedId, command.name, command.timeOccurred))
                .addNext(CapacityIncreased(command.initialCapacity))
                .toUncommittedEventsResult()
        } else {
            CommandHandlingResult(RuntimeException("Library with this id already exists"))
        }
    }

    private fun handle(command: DonateBookToLibrary): CommandHandlingResult {
        return if (root.currentInventoryCount + command.copies > root.maximumCapacity) {
            // todo: make individual Exceptions for these errors
            CommandHandlingResult(RuntimeException("Library is too full!"))
        } else {
            EventFactory<DonateBookToLibrary, Library, LibraryId>(command, root)
                .addNext(BooksDonated(command.copies))
                .toUncommittedEventsResult()
        }
    }
}

package io.cqrs.kt.librarymanager.core.library

import io.cqrs.core.Aggregate
import io.cqrs.core.Command
import io.cqrs.core.CommandHandlingResult
import io.cqrs.core.event.EventFactory
import io.cqrs.core.event.EventRepository
import io.cqrs.core.identifiers.UserId
import io.cqrs.kt.librarymanager.core.commands.FoundLibrary
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import java.lang.RuntimeException

/**
 *
 */
class LibraryAggregate(libraryId: LibraryId, private val eventRepository: EventRepository): Aggregate {

    // note that this library is mean to fairly modular, in the sense that not every mechanism *needs* to be used
    // ... a loosely coupled framework. For example, one doesn't need to use this Aggregate concept - all of this
    // code could exist in the LibraryService - but we include it and use it here as an example of structure.

    val root: Library = Library(libraryId)

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
            else -> CommandHandlingResult(RuntimeException("Unknown command received: ${command::class.java}"))
        }
    }

    private fun handle(command: FoundLibrary): CommandHandlingResult {
        // Use the EventFactory to make creating new events easier.
        return EventFactory<FoundLibrary, Library, LibraryId>(command, root)
            .addNext(LibraryFounded(command.requestedId, command.name, command.timeOccurred))
            .addNext(CapacityIncreased(command.initialCapacity))
            .toUncommittedEventsResult()
    }
}

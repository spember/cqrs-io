package io.cqrs.kt.librarymanager.core.library

import io.cqrs.core.DefaultEntity
import io.cqrs.core.event.Event
import io.cqrs.core.event.EventEnvelope
import io.cqrs.core.identifiers.EntityId
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId

/**
 * The Entity that represents the Library that is being managed by this application. The demo
 * assumes that all actions pertain to one library (e.g. not multi-tenant).
 */
class Library(id: LibraryId): DefaultEntity<LibraryId>(id) {

    var libraryName = ""
        private set

    // why 'var' for capcity? well, we could expand our library, buy more shelves, etc
    var maximumCapacity: Int = 0
        private set

    var currentInventoryCount = 0
        private set

    override fun handleEventApply(envelope: EventEnvelope<out Event, out EntityId<*>>) {
        when(val event = envelope.event) {
            is BooksDonated -> {
                currentInventoryCount += event.count
            }
        }
    }
}

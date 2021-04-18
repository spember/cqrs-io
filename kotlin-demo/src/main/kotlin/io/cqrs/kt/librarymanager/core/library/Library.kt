package io.cqrs.kt.librarymanager.core.library

import io.cqrs.core.DefaultEntity
import io.cqrs.core.event.Event
import io.cqrs.core.event.EventEnvelope
import io.cqrs.core.identifiers.EntityId
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import java.time.Instant

/**
 * The Entity that represents the Library that is being managed by this application. The demo
 * assumes that all actions pertain to one library (e.g. not multi-tenant).
 */
class Library(id: LibraryId): DefaultEntity<LibraryId>(id) {

    var libraryName = ""
        private set

    var dateFounded: Instant = Instant.now()
        private set

    // why 'var' for capcity? well, we could expand our library, buy more shelves, etc
    var maximumCapacity: Int = 0
        private set

    var currentInventoryCount = 0
        private set

    override fun handleEventApply(envelope: EventEnvelope<out Event, out EntityId<*>>) {
        // how should I apply each event as they're seen?
        // it is extremely important that these application handlers
        // remain as Pure Functions, meaning that there are no side-effects (e.g. reading/writing from a
        // data store, publishing to a message queue, making an http call... even logging if you want to
        // be dogmatic about it
        when(val event = envelope.event) {
            is LibraryFounded -> {
                libraryName = event.name
                // we can always access data from the envelope to manipulate the Entity
                dateFounded = envelope.eventCoreData.instantOccurred
            }
            is BooksDonated -> {
                currentInventoryCount += event.count
            }
            is CapacityIncreased -> {
                maximumCapacity += event.increasedCapacity
            }
        }
    }
}

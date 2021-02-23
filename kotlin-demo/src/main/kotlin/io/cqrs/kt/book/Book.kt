package io.cqrs.kt.book

import io.cqrs.core.DefaultEntity
import io.cqrs.core.event.Event
import io.cqrs.core.event.EventEnvelope
import io.cqrs.core.identifiers.EntityId
import io.cqrs.kt.identifiers.BookId
import java.time.Instant

/**
 * A book represents an individual copy of a book in our library
 *
 * A book has both a unique id and an ISBN - reflecting that there are multiple, individual copies of a single ISBN
 *
 *
 */
class Book(id: BookId): DefaultEntity<BookId>(id) {
    // only capture state changes... e.g. the number of pages, the author won't change, and could eventually be
    // encapsulated in a Value object called ISBN or something

    var dateObtained: Instant = Instant.now()
        private set

    var lastCheckedOut: Instant? = null
        private set

    var lastReturned: Instant? = null
        private set

    var isbn = ""

    var inStock = true
        private set

    var timesCheckedOut = 0

    override fun handleEventApply(envelope: EventEnvelope<out Event, out EntityId<*>>) {
        val event = envelope.event
        when(event){
            is BookDonated -> {
                dateObtained = envelope.eventCoreData.instantOccurred
                isbn = event.isbn
            }
            is BookCheckedOut -> {
                inStock = false
                timesCheckedOut++
                lastCheckedOut = Instant.now()
                lastReturned = null
            }
            is BookReturned -> {
                inStock = true
                lastReturned = Instant.now()
            }
        }
    }
}

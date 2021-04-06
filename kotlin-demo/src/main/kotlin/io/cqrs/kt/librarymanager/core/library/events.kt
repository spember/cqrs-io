package io.cqrs.kt.librarymanager.core.library

import io.cqrs.core.event.Event
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import java.time.Instant

/**
 * Signifies that books were donated to the library
 */
class BooksDonated(val count: Int): Event

/**
 *
 */
class LibraryFounded(val id: LibraryId, val name: String, val dateFounded: Instant): Event

/**
 *
 */
class CapacityIncreased(val increasedCapacity: Int): Event

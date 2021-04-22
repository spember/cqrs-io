package io.cqrs.kt.librarymanager.core.commands

import io.cqrs.core.Command
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import io.cqrs.kt.librarymanager.core.identifiers.LibraryStaffId
import java.time.Instant

/**
 *
 */
class FoundLibrary(
    userId: LibraryStaffId,
    val requestedId: LibraryId, // normally creating an entity would generate the id for you
    val name: String,
    val initialCapacity: Int
) : Command<LibraryStaffId>(userId, Instant.now())

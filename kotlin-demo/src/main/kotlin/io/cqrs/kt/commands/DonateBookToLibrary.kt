package io.cqrs.kt.commands

import io.cqrs.core.DefaultCommand
import io.cqrs.kt.identifiers.LibraryStaffId
import java.time.Instant

/**
 *
 */
class DonateBookToLibrary(
    userId: LibraryStaffId,
    val isbn: String
    ): DefaultCommand<LibraryStaffId>(userId, Instant.now())

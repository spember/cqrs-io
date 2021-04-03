package io.cqrs.kt.bookstore.core.commands

import io.cqrs.core.DefaultCommand
import io.cqrs.kt.bookstore.core.identifiers.LibraryStaffId
import java.time.Instant

/**
 *
 */
class DonateBookToLibrary(
    userId: LibraryStaffId,
    val isbn: String
    ): DefaultCommand<LibraryStaffId>(userId, Instant.now())

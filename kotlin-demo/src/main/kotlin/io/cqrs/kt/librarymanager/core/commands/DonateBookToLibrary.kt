package io.cqrs.kt.librarymanager.core.commands

import io.cqrs.core.DefaultCommand
import io.cqrs.kt.librarymanager.core.book.PublishedBook
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import io.cqrs.kt.librarymanager.core.identifiers.LibraryStaffId
import java.time.Instant

/**
 * A shipment of donations has been received and needs to be entered into the system by the staff.
 */
class DonateBookToLibrary(
    userId: LibraryStaffId,
    val libraryId: LibraryId,
    val publishedBook: PublishedBook,
    val copies: Int = 1
    ): DefaultCommand<LibraryStaffId>(userId, Instant.now())

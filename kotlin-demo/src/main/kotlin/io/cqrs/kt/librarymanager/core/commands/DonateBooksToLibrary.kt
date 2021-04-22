package io.cqrs.kt.librarymanager.core.commands

import io.cqrs.core.Command
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import io.cqrs.kt.librarymanager.core.identifiers.LibraryStaffId
import java.time.Instant

/**
 * A shipment of donations has been received and needs to be entered into the system by the staff.
 */
class DonateBooksToLibrary(
    userId: LibraryStaffId,
    val libraryId: LibraryId,
    val books: List<CommandRow>
    ): Command<LibraryStaffId>(userId, Instant.now()) {
        class CommandRow(val isbn: String, val copies: Int = 1)

        fun totalCopies(): Int =
            books.sumBy { commandRow -> commandRow.copies }
    }

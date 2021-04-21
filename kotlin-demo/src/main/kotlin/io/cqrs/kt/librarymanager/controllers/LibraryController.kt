package io.cqrs.kt.librarymanager.controllers

import io.cqrs.core.event.EventRepository
import io.cqrs.kt.librarymanager.app.Utils
import io.cqrs.kt.librarymanager.core.book.PublishedBookRepository
import io.cqrs.kt.librarymanager.core.commands.DonateBooksToLibrary
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import io.cqrs.kt.librarymanager.core.library.LibraryAggregate
import io.cqrs.kt.librarymanager.core.services.LibraryService
import io.cqrs.kt.librarymanager.dto.BookDonationRequest
import io.cqrs.kt.librarymanager.dto.LibraryResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import javax.inject.Inject

/**
 *
 */
@Controller("/library", produces = [MediaType.APPLICATION_JSON])
class LibraryController(
    @Inject private val libraryService: LibraryService,
    @Inject private val publishedBookRepository: PublishedBookRepository,
    @Inject private val eventRepository: EventRepository
    ) {

    @Get(value="/{libraryId}")
    fun checkoutBook(@PathVariable libraryId: String): LibraryResponse? {
        val aggregate = LibraryAggregate(LibraryId(libraryId), eventRepository)
        return if (aggregate.root.isBare) {
            null
        } else {
            LibraryResponse.fromLibrary(aggregate.root)
        }
    }

    /**
     * Informs that books are being donated to the library
     */
    @Post("/{libraryId}/donations")
    fun donateBooks(@PathVariable libraryId: String, @Body donationRequest: BookDonationRequest) {
        // note: this is a big one from an illustrative purpose, and in my opinion shows some of the true power
        // of Event Sourced systems: we're about to update and/or create multiple entities, utilizing just one
        // additive only database write

        val command = DonateBooksToLibrary(
            Utils.retrieveLibraryStaffIdFromHeader(),
            LibraryId(libraryId),
            donationRequest.books.map { donationRow: BookDonationRequest.DonationRow ->
                DonateBooksToLibrary.CommandRow(donationRow.isbn, donationRow.copies)
            }
        )
        val result = libraryService.handle(command)
        if (result.maybeError().isPresent) {
            throw result.maybeError().get()
        }
    }

}

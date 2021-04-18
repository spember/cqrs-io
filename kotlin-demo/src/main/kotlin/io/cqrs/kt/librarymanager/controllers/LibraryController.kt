package io.cqrs.kt.librarymanager.controllers

import io.cqrs.core.event.EventRepository
import io.cqrs.kt.librarymanager.core.identifiers.LibraryId
import io.cqrs.kt.librarymanager.core.library.LibraryAggregate
import io.cqrs.kt.librarymanager.dto.LibraryResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import javax.inject.Inject

/**
 *
 */
@Controller("/library", produces = [MediaType.APPLICATION_JSON])
class LibraryController(@Inject private val eventRepository: EventRepository) {

    @Get(value="/{libraryId}")
    fun checkoutBook(@PathVariable libraryId: String): LibraryResponse? {
        println("libraryId is ${libraryId}")
        val aggregate = LibraryAggregate(LibraryId(libraryId), eventRepository).loadCurrentState()

        return if (aggregate.root.isBare) {
            null
        } else {
            LibraryResponse.fromLibrary(aggregate.root)
        }
    }

}

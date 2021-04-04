package io.cqrs.kt.librarymanager.controllers

import io.cqrs.kt.librarymanager.core.book.PublishedBook
import io.cqrs.kt.librarymanager.core.book.PublishedBookRepository
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import java.time.LocalDate
import javax.inject.Inject
import kotlin.streams.toList


/**
 * Expose the Published Books our store is capable of ordering
 */
@Controller("/published-books", produces = [MediaType.APPLICATION_JSON])
class PublishedBookController {

    @Inject
    lateinit var publishedBookRepository: PublishedBookRepository

    @Get(value="/")
    fun index(@QueryValue partialTitle: String?): PublishedBookResponse {
        return if (partialTitle == null) {
            PublishedBookResponse(listOf(publishedBookRepository.list().findFirst().get()))
        } else {
            PublishedBookResponse(publishedBookRepository.findAllByTitle(partialTitle).toList())
        }
    }

    @Get(value="/{isbn}")
    fun byIsbn(@PathVariable isbn: String): PublishedBook? =
        publishedBookRepository.findByIsbn(isbn)
}

data class PublishedBookResponse(val books: List<PublishedBook>)

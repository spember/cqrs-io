package io.cqrs.kt.bookstore.controllers

import io.cqrs.kt.bookstore.core.book.PublishedBook
import io.cqrs.kt.bookstore.core.book.PublishedBookRepository
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.time.LocalDate
import javax.inject.Inject


/**
 * Expose the Published Books our store is capable of ordering
 */
@Controller("/published-books")
class PublishedBookController {

    @Inject
    lateinit var publishedBookRepository: PublishedBookRepository

    @Get(value="/", produces = [MediaType.APPLICATION_JSON])
    fun index(): PublishedBook {
        return publishedBookRepository.list().findFirst().get()
    }
}

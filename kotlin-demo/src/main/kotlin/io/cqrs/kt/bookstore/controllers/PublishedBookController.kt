package io.cqrs.kt.bookstore.controllers

import io.cqrs.kt.bookstore.core.book.PublishedBook
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.time.LocalDate


/**
 * Expose the Published Books our store is capable of ordering
 */
@Controller("/published-books")
class PublishedBookController {

    @Get(value="/", produces = [MediaType.APPLICATION_JSON])
    fun index(): PublishedBook {
        return PublishedBook("The Chronicles of Test", "Test Testington", "123", 25, LocalDate.now())
    }
}

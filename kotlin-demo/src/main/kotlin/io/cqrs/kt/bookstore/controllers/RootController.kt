package io.cqrs.kt.bookstore.controllers

import io.cqrs.kt.bookstore.core.book.PublishedBook
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.time.LocalDate

/**
 *
 */
@Controller("/")
class RootController {

    @Get(value="/", produces = [MediaType.TEXT_PLAIN])
    fun index(): String {
        return "Testing"
    }

}

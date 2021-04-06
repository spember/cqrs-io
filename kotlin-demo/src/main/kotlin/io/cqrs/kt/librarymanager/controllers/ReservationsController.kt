package io.cqrs.kt.librarymanager.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

/**
 * Governing actions taken to check in , check out, check if available
 */
@Controller("/reservations", produces = [MediaType.APPLICATION_JSON])
class ReservationsController {

    // check in

    // check out
    @Get(value="/checkout/{bookId}")
    fun checkoutBook(@PathVariable bookId: String): Boolean {
        // return Receipt?
        return true
    }

    // check availability - this would / should require a projection derived from the events to build up this lookup
    // table. This could also be functionality that lists on the Library Entity / Aggregate
}

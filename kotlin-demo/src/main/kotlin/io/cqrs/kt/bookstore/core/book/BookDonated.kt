package io.cqrs.kt.bookstore.core.book

import io.cqrs.core.event.Event

/**
 *
 */
class BookDonated(val isbn: String): Event

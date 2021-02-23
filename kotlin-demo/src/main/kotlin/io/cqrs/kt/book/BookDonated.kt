package io.cqrs.kt.book

import io.cqrs.core.event.Event

/**
 *
 */
class BookDonated(val isbn: String): Event

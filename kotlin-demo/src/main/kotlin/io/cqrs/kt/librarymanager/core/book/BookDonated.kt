package io.cqrs.kt.librarymanager.core.book

import io.cqrs.core.event.Event

/**
 *
 */
class BookDonated(val sourceBook: PublishedBook): Event

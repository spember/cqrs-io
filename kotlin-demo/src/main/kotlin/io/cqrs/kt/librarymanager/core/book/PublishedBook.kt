package io.cqrs.kt.librarymanager.core.book

import java.time.LocalDate

/**
 * A Value Object representing a book that has been published in the wide world.
 * The Bookstore is concerned with the lifecycle of specific copies of a published book
 */
data class PublishedBook(
    val title: String,
    val author: String,
    val isbn: String,
    val pageCount: Int,
    val publishDate: LocalDate
    )

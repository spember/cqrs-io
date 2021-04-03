package io.cqrs.kt.bookstore.db

import io.cqrs.kt.bookstore.core.book.PublishedBook
import io.cqrs.kt.bookstore.core.book.PublishedBookRepository
import java.time.LocalDate
import java.util.stream.Stream

/**
 *
 */
class InMemoryPublishBookRepository: PublishedBookRepository {

    private val publishedBooks = listOf(
        PublishedBook("The Chronicles of Test", "Test Testington", "123", 25, LocalDate.now()),
        PublishedBook("The Stand", "Stephen King", "scary1", 1480, LocalDate.now().minusYears(30)),
        PublishedBook("Domain Driven Design", "Eric Evans", "ddd4life", 450, LocalDate.now().minusYears(19)),
    )

    override fun findByIsbn(requestedIsbn: String): PublishedBook? =
        publishedBooks.find { publishedBook -> publishedBook.isbn == requestedIsbn }

    override fun findAllByTitle(title: String): Stream<PublishedBook> =
        publishedBooks.filter { it.title.contains(title) }.stream()

    override fun list(): Stream<PublishedBook> = publishedBooks.stream()
}

package io.cqrs.kt.bookstore.core.book

import java.util.stream.Stream

/**
 * For querying of available books before ordering a shipment for the store.
 */
interface PublishedBookRepository {

    /**
     * Retrieve a specific PublishedBook by ISBN value, if one exists
     */
    fun findByIsbn(requestedIsbn: String): PublishedBook?

    /**
     * Retrieve all books by title match. Implementations should attempt partial matches (e.g. a 'like' query)
     */
    fun findAllByTitle(title: String): Stream<PublishedBook>

}

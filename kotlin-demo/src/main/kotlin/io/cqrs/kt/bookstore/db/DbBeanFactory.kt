package io.cqrs.kt.bookstore.db

import io.cqrs.kt.bookstore.core.book.PublishedBookRepository
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

/**
 * Responsible for creating the beans for Micronaut dependency resolution
 */
@Factory
class DbBeanFactory {

    @Singleton
    fun publishBookRepository(): PublishedBookRepository = InMemoryPublishBookRepository()
}

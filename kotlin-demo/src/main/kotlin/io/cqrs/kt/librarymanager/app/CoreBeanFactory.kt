package io.cqrs.kt.librarymanager.app

import io.cqrs.core.event.EventRepository
import io.cqrs.kt.librarymanager.core.book.PublishedBookRepository
import io.cqrs.kt.librarymanager.core.services.LibraryService
import io.cqrs.kt.librarymanager.db.InMemoryEventRepository
import io.cqrs.kt.librarymanager.db.InMemoryPublishBookRepository
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

/**
 * Responsible for creating the beans out of objects in `core` for Micronaut dependency resolution
 */
@Factory
class CoreBeanFactory {

    @Singleton
    fun publishedBookRepository(): PublishedBookRepository = InMemoryPublishBookRepository()

    @Singleton
    fun eventRepository(): EventRepository = InMemoryEventRepository()

    @Singleton
    fun libraryService(
        publishedBookRepository: PublishedBookRepository,
        eventRepository: EventRepository
    ): LibraryService = LibraryService(publishedBookRepository, eventRepository)


}

package io.cqrs.kt.librarymanager.db

import io.cqrs.core.event.Event
import io.cqrs.core.event.EventEnvelope
import io.cqrs.core.event.EventRepository
import io.cqrs.core.identifiers.EntityId
import org.slf4j.LoggerFactory

/**
 * An in-memory implementation of the EventRepository. Data will not be persisted between startups, of course,
 * and this is merely used for example purposes.
 */
class InMemoryEventRepository: EventRepository {
    private val journal = mutableListOf<EventEnvelope<out Event, out EntityId<*>>>()

    override fun <EI : EntityId<*>> listAllForEntity(entityId: EI): List<EventEnvelope<out Event, out EntityId<*>>> =
        journal
            .filter { eventEnvelope -> eventEnvelope.eventCoreData.entityId == entityId }
            .sortedBy { eventEnvelope -> eventEnvelope.eventCoreData.revision }

    override fun write(events: List<EventEnvelope<out Event, out EntityId<*>>>) {
        journal.addAll(events)
        log.debug("Successfully recorded ${events.size} events. Event Store now contains ${journal.size} events")
    }

    fun reset() {
        journal.removeAll { it.eventCoreData.revision > 2 }
    }

    companion object {
        private val log = LoggerFactory.getLogger(InMemoryEventRepository::class.java)
    }

}

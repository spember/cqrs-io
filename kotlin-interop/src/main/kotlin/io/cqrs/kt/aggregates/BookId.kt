package io.cqrs.kt.aggregates

import io.cqrs.core.identifiers.EntityId
import java.util.UUID

/**
 *
 */
class BookId(value: UUID) : EntityId<UUID>(value)

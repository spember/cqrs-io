package io.cqrs.kt.librarymanager.core.identifiers

import io.cqrs.core.identifiers.EntityId
import java.util.UUID

/**
 *
 */
data class BookId(val value: UUID) : EntityId<UUID>(value)
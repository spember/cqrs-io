package io.cqrs.kt.identifiers

import io.cqrs.core.identifiers.EntityId
import java.util.UUID

/**
 *
 */
class BookId(value: String) : EntityId<String>(value)

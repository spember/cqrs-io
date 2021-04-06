package io.cqrs.kt.librarymanager.core.identifiers

import io.cqrs.core.identifiers.EntityId

/**
 *
 */
data class LibraryId(val value: String) : EntityId<String>(value)

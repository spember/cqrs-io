package io.cqrs.kt.librarymanager.core.identifiers

import io.cqrs.core.identifiers.UserId

/**
 * Signifies
 */
data class LibraryStaffId(val value: String): UserId<String>(value)

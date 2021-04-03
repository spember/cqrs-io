package io.cqrs.kt.bookstore.core.identifiers

import io.cqrs.core.identifiers.UserId

/**
 * Signifies
 */
class LibraryStaffId(value: String): UserId<String>(value)

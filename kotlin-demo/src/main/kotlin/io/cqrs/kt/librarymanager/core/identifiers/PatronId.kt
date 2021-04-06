package io.cqrs.kt.librarymanager.core.identifiers

import io.cqrs.core.identifiers.UserId

/**
 * Identifier of a Patron of the library, someone who is checking out books
 */
class PatronId(val value: String): UserId<String>(value)

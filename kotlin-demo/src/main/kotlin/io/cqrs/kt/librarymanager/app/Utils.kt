package io.cqrs.kt.librarymanager.app

import io.cqrs.kt.librarymanager.core.identifiers.LibraryStaffId

object Utils {

    /**
     * Retrieves the staff id from Headers
     *
     * Note: this returns a static id and is just for demonstrative purposes.
     * In a real system you'd want to retrieve the current user from Headers & verify it,
     * or more likely use a JWT or security principal
     */
    fun retrieveLibraryStaffIdFromHeader(): LibraryStaffId {
        return LibraryStaffId("HeadArchivist@samplelib.org")
    }
}

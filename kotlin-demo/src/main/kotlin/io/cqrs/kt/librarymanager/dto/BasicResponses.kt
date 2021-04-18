package io.cqrs.kt.librarymanager.dto

import io.cqrs.kt.librarymanager.core.book.PublishedBook
import io.cqrs.kt.librarymanager.core.library.Library

data class PublishedBookResponse(val books: List<PublishedBook>)

data class LibraryResponse(val name: String, val archivedVolumes: Int) {
    companion object {
        @JvmStatic
        fun fromLibrary(library: Library): LibraryResponse {
            return LibraryResponse(library.libraryName, library.currentInventoryCount)
        }
    }
}

package io.cqrs.kt.librarymanager.dto

/**
 *
 */
class BookDonationRequest(val books: List<DonationRow>) {

    class DonationRow(val isbn: String, val copies: Int)
}

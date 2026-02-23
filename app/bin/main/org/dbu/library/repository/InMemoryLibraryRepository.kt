package org.dbu.library.repository

import org.dbu.library.model.Book
import org.dbu.library.model.Patron

class InMemoryLibraryRepository : LibraryRepository {

    private val books = mutableMapOf<String, Book>()
    private val patrons = mutableMapOf<String, Patron>()

    // TODO: Implement the methods to manage books and patrons in memory
    override fun addBook(book: Book) {
        books[book.isbn] = book
    }

    override fun getAllBooks(): List<Book> {
        return books.values.toList()
    }

    override fun findBookByIsbn(isbn: String): Book? {
        return books[isbn]
    }

    override fun addPatron(patron: Patron) {
    patrons[patron.id] = patron
}

    override fun findPatronById(id: String): Patron? {
        return patrons[id]
    }

    override fun getAllPatrons(): List<Patron> {
        return patrons.values.toList()
    }
}
package org.dbu.library.service

import org.dbu.library.model.Book
import org.dbu.library.repository.LibraryRepository

class DefaultLibraryService(
    private val repository: LibraryRepository
) : LibraryService {

//  TODO: implement the methods
private val MAX_BORROW_LIMIT = 3

    override fun addBook(book: Book) {
        repository.addBook(book)
    }

    override fun registerPatron(id: String, name: String, email: String, phone: String) {
        val patron = org.dbu.library.model.Patron(
            id = id,
            name = name,
            email = email,
            phone = phone,
            borrowedBooks = mutableListOf()
        )
        repository.addPatron(patron)
    }

    override fun borrowBook(patronId: String, isbn: String): Boolean {
        val patron = repository.findPatronById(patronId) ?: return false
        val book = repository.findBookByIsbn(isbn) ?: return false

        if (!book.isAvailable) return false
        if (patron.borrowedBooks.size >= MAX_BORROW_LIMIT) return false

        book.isAvailable = false
        patron.borrowedBooks.add(book)
        return true
    }

    override fun returnBook(patronId: String, isbn: String): Boolean {
        val patron = repository.findPatronById(patronId) ?: return false
        val book = patron.borrowedBooks.find { it.isbn == isbn } ?: return false

        patron.borrowedBooks.remove(book)
        book.isAvailable = true
        return true
    }

    override fun searchBooks(query: String): List<Book> {
        val lower = query.trim().lowercase()

        return repository.getAllBooks().filter {
            it.title.trim().lowercase().contains(lower) ||
            it.author.trim().lowercase().contains(lower)
    }
}

    override fun listAllBooks(): List<Book> {
        return repository.getAllBooks()
    }

    override fun listAllPatrons(): List<org.dbu.library.model.Patron> {
        return repository.getAllPatrons()
    }
}
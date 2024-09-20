package org.lib.book

import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    fun findByIsbn(isbn: String): Book? {
        return bookRepository.findByIsbn(isbn)
    }

    fun findAllBooks(): List<Book> {
        return bookRepository.findAll()
    }

    fun saveBook(book: Book): Book {
        return bookRepository.save(book)
    }

    fun removeBook(book: Book) {
        return bookRepository.delete(book)
    }
}

package org.lib.book

import org.springframework.data.mongodb.repository.MongoRepository

interface BookRepository : MongoRepository<Book, String> {
    fun findByIsbn(isbn: String): Book?
}

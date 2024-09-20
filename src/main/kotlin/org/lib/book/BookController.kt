package org.lib.book

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController @Autowired constructor(
    private val bookService: BookService
) {

    @GetMapping
    fun getArts(): List<Book> {
        return bookService.findAllBooks()
    }

    @GetMapping("/findByIsbn")
    fun getBookByIsbn(@RequestParam isbn: String): Book? {
        return bookService.findByIsbn(isbn)
    }

    @PostMapping("/add")
    fun addBook(@RequestBody book: Book): ResponseEntity<Book> {
        val createdBook = bookService.saveBook(book)
        return ResponseEntity(createdBook, HttpStatus.CREATED)
    }

    @PostMapping("/remove")
    fun removeBook(@RequestParam isbn: String): ResponseEntity<Void> {
        return try {
            val book = bookService.findByIsbn(isbn)
            if (book != null) {
                bookService.removeBook(book)
                ResponseEntity(HttpStatus.NO_CONTENT)
            } else {
                ResponseEntity(HttpStatus.NOT_FOUND)
            }
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}

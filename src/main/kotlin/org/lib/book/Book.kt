package org.lib.book

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "books")
data class Book(
    @Id val id: String? = null,
    val isbn: String,
    val title: String,
    val author: String
)

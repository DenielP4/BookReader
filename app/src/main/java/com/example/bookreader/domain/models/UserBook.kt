package com.example.bookreader.domain.models

data class UserBook(
    val name: String,
    val author: String,
    val image: String,
    val file: String,
    val bookId: Int
)
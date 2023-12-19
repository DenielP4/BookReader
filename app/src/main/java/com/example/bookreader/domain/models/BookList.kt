package com.example.bookreader.domain.models

data class BookList(
    val name: String,
    val author: String,
    var rate: Int,
    val image: String,
    val genre: String,
    val id: Int
)

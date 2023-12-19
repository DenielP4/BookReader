package com.example.bookreader.data.remote.responses

data class Book(
    val name: String,
    val author: String,
    var rate: Int,
    val description: String,
    val image: String,
    val file: String,
    var markReadFlag: Boolean,
    val genre: String,
    var reviews: MutableList<Review>?,
    val id: Int
)
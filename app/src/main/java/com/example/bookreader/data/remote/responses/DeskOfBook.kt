package com.example.bookreader.data.remote.responses

data class DeskOfBook(
    var deskName: String,
    var numberOfBooks: Int,
    var location: String,
    var category: String,
    var books: MutableList<Book>?,
    val id: Int
)

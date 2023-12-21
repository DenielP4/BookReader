package com.example.bookreader.data.remote.responses

data class User(
    val name: String,
    val registerDate: String,
    var password: String,
    var shelf: MutableList<DeskOfBook>?,
    val id: Int
)

package com.example.bookreader.domain.models

import com.example.bookreader.data.remote.responses.Review

data class BookInfo(
    val name: String,
    val author: String,
    var rate: Int,
    val description: String,
    val image: String,
    var reviews: MutableList<Review>?,
    val id: Int
)
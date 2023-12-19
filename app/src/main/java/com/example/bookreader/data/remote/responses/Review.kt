package com.example.bookreader.data.remote.responses

data class Review(
    var reviewText: String,
    var rating: Double,
    val reviewDate: String,
    val id: Int
)

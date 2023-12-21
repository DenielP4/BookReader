package com.example.bookreader.data.remote.responses


data class Review(
    val reviewText: String,
    val rating: Int,
    val reviewDate: String,
    val userId: Int
)

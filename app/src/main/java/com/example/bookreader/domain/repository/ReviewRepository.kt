package com.example.bookreader.domain.repository

import com.example.bookreader.data.remote.responses.Review

interface ReviewRepository {
    suspend fun addReview(id: Int, review: Review)
    suspend fun deleteReview(id: Int)
}
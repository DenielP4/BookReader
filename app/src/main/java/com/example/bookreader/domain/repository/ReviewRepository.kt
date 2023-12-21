package com.example.bookreader.domain.repository

import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.responses.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun addReview(id: Int, review: Review)
    suspend fun deleteReview(id: Int)
    fun getReviewList(bookId: Int): Resource<List<Review>>
}
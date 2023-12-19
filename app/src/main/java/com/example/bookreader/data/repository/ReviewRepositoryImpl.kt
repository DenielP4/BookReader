package com.example.bookreader.data.repository

import com.example.bookreader.data.remote.BooksApi
import com.example.bookreader.data.remote.responses.Review
import com.example.bookreader.domain.repository.ReviewRepository

class ReviewRepositoryImpl(
    private val api: BooksApi
) : ReviewRepository {
    override suspend fun addReview(id: Int, review: Review) {
        api.addReview(id, review)
    }

    override suspend fun deleteReview(id: Int) {
        api.deleteReview(id)
    }
}
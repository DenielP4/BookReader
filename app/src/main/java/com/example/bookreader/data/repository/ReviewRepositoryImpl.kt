package com.example.bookreader.data.repository

import android.util.Log
import com.example.bookreader.common.Resource
import com.example.bookreader.data.remote.BooksApi
import com.example.bookreader.data.remote.responses.Review
import com.example.bookreader.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow

class ReviewRepositoryImpl(
    private val api: BooksApi
) : ReviewRepository {
    override suspend fun addReview(id: Int, review: Review) {
        api.addReview(id, review)
    }

    override suspend fun deleteReview(id: Int) {
        api.deleteReview(id)
    }

    override fun getReviewList(bookId: Int): Resource<List<Review>> {
        val response = try {
            api.getReviewList(bookId)
        } catch (e: Exception) {
            return Resource.Error("Отзывы не получены")
        }
        return Resource.Success(response)
    }
}
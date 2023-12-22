package com.example.bookreader.presentation.AddReviewScreen

import com.example.bookreader.presentation.BookInfoScreen.BookInfoEvent

sealed class AddReviewEvent {
    object OnLoad: AddReviewEvent()
    object OnClickAddReview: AddReviewEvent()
    data class OnReviewTextChange(val text: String): AddReviewEvent()
    data class OnReviewChangeRating(val rating: Float): AddReviewEvent()
}

package com.example.bookreader.presentation.AddReviewScreen

import com.example.bookreader.presentation.BookInfoScreen.BookInfoEvent

sealed class AddReviewEvent {
    object OnLoad: AddReviewEvent()
}

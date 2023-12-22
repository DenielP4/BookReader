package com.example.bookreader.presentation.BookInfoScreen

import com.example.bookreader.presentation.SearchBookScreen.SearchBookEvent

sealed class BookInfoEvent {
    object OnLoad: BookInfoEvent()
    object OnLoadBook: BookInfoEvent()
    data class OnClickAddReview(val route: String): BookInfoEvent()
    object OnClickAddBook: BookInfoEvent()
}

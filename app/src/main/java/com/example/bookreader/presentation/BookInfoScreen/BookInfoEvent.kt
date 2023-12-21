package com.example.bookreader.presentation.BookInfoScreen

import com.example.bookreader.presentation.SearchBookScreen.SearchBookEvent

sealed class BookInfoEvent {
    object OnLoad: BookInfoEvent()
}

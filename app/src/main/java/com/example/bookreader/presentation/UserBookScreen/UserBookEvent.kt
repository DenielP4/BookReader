package com.example.bookreader.presentation.UserBookScreen

import com.example.bookreader.presentation.FilterScreen.Filter
import com.example.bookreader.presentation.ProfileScreen.ProfileEvent
import com.example.bookreader.presentation.SearchBookScreen.SearchBookEvent
import com.example.bookreader.presentation.UserBookScreen.resourses.Menu

sealed class UserBookEvent {
    object OnLoad: UserBookEvent()
    data class OnClickAuth(val route: String): UserBookEvent()
    data class OnClickBook(val route: String): UserBookEvent()
    data class OnChangeSort(val sort: Menu): UserBookEvent()
}

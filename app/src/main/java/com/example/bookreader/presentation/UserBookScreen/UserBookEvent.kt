package com.example.bookreader.presentation.UserBookScreen

import com.example.bookreader.presentation.ProfileScreen.ProfileEvent

sealed class UserBookEvent {
    object OnLoad: UserBookEvent()
}
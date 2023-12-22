package com.example.bookreader.presentation.BookInfoScreen.dialog

sealed class DialogEvent {
    object OnCancel: DialogEvent()
    data class OnAuth(val route: String): DialogEvent()
}

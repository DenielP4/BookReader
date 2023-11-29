package com.example.bookreader.presentation.utils

sealed class UiEvent{
    object PopBackStack: UiEvent()

    data class Navigate(val route: String): UiEvent()

    data class ShowSnackBar(val message: String): UiEvent()
}

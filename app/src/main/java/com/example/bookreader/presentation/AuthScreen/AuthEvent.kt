package com.example.bookreader.presentation.AuthScreen

import com.example.bookreader.presentation.RegScreen.RegEvent

sealed class AuthEvent {
    object OnUserLogin: AuthEvent()
    data class OnNameTextChange(val name: String): AuthEvent()
    data class OnPasswordTextChange(val password: String): AuthEvent()
    data class OnNavigateToRegistration(val route: String): AuthEvent()
    object OnDeleteNameText: AuthEvent()
    data class OnChangeVisiblePassword(val visible: Boolean): AuthEvent()
}

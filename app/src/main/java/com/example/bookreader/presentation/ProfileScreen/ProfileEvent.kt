package com.example.bookreader.presentation.ProfileScreen

sealed class ProfileEvent {
    data class OnClickLogOut(val route: String): ProfileEvent()
    data class OnClickAuth(val route: String): ProfileEvent()
    object OnLoad: ProfileEvent()
}

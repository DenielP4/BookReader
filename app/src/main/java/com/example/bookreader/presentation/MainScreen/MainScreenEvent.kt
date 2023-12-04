package com.example.bookreader.presentation.MainScreen

sealed class MainScreenEvent {
    data class Navigate(val route: String): MainScreenEvent()
    data class NavigateMain(val route: String): MainScreenEvent()
}

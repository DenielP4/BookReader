package com.example.bookreader.presentation.navigation

import com.example.bookreader.R
import com.example.bookreader.presentation.utils.Application
import com.example.bookreader.presentation.utils.Routes

sealed class BottomNavItem(val title: String, val iconId: Int, val route: String){
    object Profile: BottomNavItem("Профиль", R.drawable.ic_user, Application.PROFILE)
    object Search: BottomNavItem("Поиск", R.drawable.ic_planet, Application.SEARCH)
    object UserBook: BottomNavItem("Полка", R.drawable.ic_book, Application.USER_BOOK)
}
package com.example.bookreader.presentation.navigation

import com.example.bookreader.R
import com.example.bookreader.presentation.utils.Routes

sealed class BottomNavItem(val title: String, val iconId: Int, val route: String){
    object Profile: BottomNavItem("Профиль", R.drawable.ic_user, Routes.PROFILE)
    object Search: BottomNavItem("Поиск", R.drawable.ic_planet, Routes.SEARCH)
    object UserBook: BottomNavItem("Полка", R.drawable.ic_book, Routes.USER_BOOK)
}
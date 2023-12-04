package com.example.bookreader.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookreader.presentation.AuthScreen.AuthScreen
import com.example.bookreader.presentation.BookInfoScreen.BookInfoScreen
import com.example.bookreader.presentation.FilterScreen.FilterScreen
import com.example.bookreader.presentation.ProfileScreen.ProfileScreen
import com.example.bookreader.presentation.RegScreen.RegScreen
import com.example.bookreader.presentation.SearchBookScreen.SearchBookScreen
import com.example.bookreader.presentation.UserBookScreen.UserBookScreen
import com.example.bookreader.presentation.utils.Routes

@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    onNavigate: (String) -> Unit
) {
    val time = 700
    NavHost(
        navController = navController,
        startDestination = Routes.SEARCH
    ) {
        composable(
            route = Routes.USER_BOOK
        ) {
            UserBookScreen()
        }
        composable(
            route = Routes.PROFILE
        ) {
            ProfileScreen(navController)
        }
        composable(
            route = Routes.SEARCH
        ) {
            SearchBookScreen(){
                onNavigate(it)
            }
        }
    }
}
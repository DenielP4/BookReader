package com.example.bookreader.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookreader.presentation.BookInfoScreen.BookInfoScreen
import com.example.bookreader.presentation.ProfileScreen.ProfileScreen
import com.example.bookreader.presentation.SearchBookScreen.SearchBookScreen
import com.example.bookreader.presentation.SplashScreen.SplashScreen
import com.example.bookreader.presentation.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()

    val time = 700

    NavHost(
        navController = navController,
        startDestination = Routes.CURRENT_BOOK,

        ) {
        composable(
            route = Routes.CURRENT_BOOK,
            enterTransition = {
                fadeIn(animationSpec = tween(time)) + slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    tween(time)
                )
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(time)) + slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    tween(time)
                )
            },
        ) {
            BookInfoScreen(navController)
        }

        composable(Routes.LOADING) {
            SplashScreen(navController)
        }
    }
}
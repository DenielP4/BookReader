package com.example.bookreader.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookreader.presentation.ProfileScreen.ProfileScreen
import com.example.bookreader.presentation.SplashScreen.SplashScreen
import com.example.bookreader.presentation.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()

    val time = 700

    NavHost(
        navController = navController,
        startDestination = Routes.LOADING,

        ) {
        composable(
            route = Routes.PROFILE,
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
            ProfileScreen(navController)
        }
        composable(Routes.LOADING) {
            SplashScreen(navController)
        }
    }
}
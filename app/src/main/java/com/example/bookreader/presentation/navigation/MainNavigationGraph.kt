package com.example.bookreader.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookreader.presentation.AddReviewScreen.AddReviewScreen
import com.example.bookreader.presentation.BookInfoScreen.BookInfoScreen
import com.example.bookreader.presentation.FilterScreen.FilterScreen
import com.example.bookreader.presentation.MainScreen.MainScreen
import com.example.bookreader.presentation.ProfileScreen.ProfileScreen
import com.example.bookreader.presentation.SearchBookScreen.SearchBookScreen
import com.example.bookreader.presentation.SplashScreen.SplashScreen
import com.example.bookreader.presentation.UserBookScreen.UserBookScreen
import com.example.bookreader.presentation.utils.Root
import com.example.bookreader.presentation.utils.Routes

@Composable
fun MainNavigationGraph() {
    val navController = rememberNavController()

    val time = 700

    NavHost(
        navController = navController,
        startDestination = Root.LOADING
        ) {
        composable(
            route = Root.MAIN_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    tween(time)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    tween(time)
                )
            },
        ) {
            MainScreen()
        }
        composable(Root.LOADING) {
            SplashScreen(navController)
        }

    }
}

@Composable
fun <T> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
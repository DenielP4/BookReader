package com.example.bookreader.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.bookreader.presentation.AddReviewScreen.AddReviewScreen
import com.example.bookreader.presentation.AuthScreen.AuthScreen
import com.example.bookreader.presentation.BookInfoScreen.BookInfoScreen
import com.example.bookreader.presentation.FilterScreen.FilterScreen
import com.example.bookreader.presentation.ProfileScreen.ProfileScreen
import com.example.bookreader.presentation.RegScreen.RegScreen
import com.example.bookreader.presentation.SearchBookScreen.SearchBookScreen
import com.example.bookreader.presentation.UserBookScreen.UserBookScreen
import com.example.bookreader.presentation.utils.Application
import com.example.bookreader.presentation.utils.BookInformationScreen
import com.example.bookreader.presentation.utils.ReviewScreen
import com.example.bookreader.presentation.utils.Routes
import com.example.bookreader.presentation.utils.SearchScreen

@Composable
fun AppNavigationGraph(
    navController: NavHostController
) {
    val time = 700
    NavHost(
        navController = navController,
        startDestination = Application.SEARCH
    ) {
        composable(
            route = Application.USER_BOOK
        ) {
            UserBookScreen()
        }
        composable(
            route = Application.PROFILE
        ) {
            ProfileScreen(navController)
        }
        composable(
            route = Application.SEARCH
        ) {
            SearchBookScreen() { route ->
                navController.navigate(route)
            }
        }
        filtersNavGraph(navController)
        booksNavGraph(navController)
    }
}

fun NavGraphBuilder.filtersNavGraph(navController: NavHostController) {
    val time = 500
    navigation(
        route = Application.FILTER,
        startDestination = SearchScreen.FILTER
    ) {
        composable(
            route = SearchScreen.FILTER,
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
            FilterScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.booksNavGraph(navController: NavHostController) {
    val time = 500
    navigation(
        route = Application.BOOK_INFO,
        startDestination = BookInformationScreen.BOOK_INFO
    ) {
        composable(
            route = BookInformationScreen.BOOK_INFO,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(time)
                )
            },
            popEnterTransition = {
                fadeIn()
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(time)
                )
            },
        ) {
            BookInfoScreen(navController) { route ->
                navController.navigate(route)
            }
        }
        composable(
            route = BookInformationScreen.REVIEW,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(time)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(time)
                )
            },
        ) {
            AddReviewScreen(navController = navController)
        }
    }
}
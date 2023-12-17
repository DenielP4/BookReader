package com.example.bookreader.presentation.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import com.example.bookreader.presentation.PickBookScreen.PickBookScreen
import com.example.bookreader.presentation.ProfileScreen.ProfileScreen
import com.example.bookreader.presentation.RegScreen.RegScreen
import com.example.bookreader.presentation.SearchBookScreen.SearchBookScreen
import com.example.bookreader.presentation.UserBookScreen.UserBookScreen
import com.example.bookreader.presentation.utils.Application
import com.example.bookreader.presentation.utils.BookInformationScreen
import com.example.bookreader.presentation.utils.ProfileScreen
import com.example.bookreader.presentation.utils.ReviewScreen
import com.example.bookreader.presentation.utils.Routes
import com.example.bookreader.presentation.utils.SearchScreen
import com.example.bookreader.presentation.utils.UserBookScreen

@Composable
fun AppNavigationGraph(
    navController: NavHostController
) {
    val time = 500
    NavHost(
        navController = navController,
        startDestination = Application.SEARCH
    ) {
        composable(
            route = Application.USER_BOOK,
        ) {
            UserBookScreen() { route ->
                navController.navigate(route)
            }
        }
        composable(
            route = Application.PROFILE,
            exitTransition = {
                when (targetState.destination.route) {
                    ProfileScreen.AUTHORIZATION -> {
                        slideOutVertically(
                            targetOffsetY = { it/it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("PROFILE", "exit") }
                    }

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    ProfileScreen.AUTHORIZATION ->
                        slideInVertically(
                            initialOffsetY = { 0 },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("PROFILE", "popEnter") }

                    else -> null
                }
            },
        ) {
            ProfileScreen() { route ->
                navController.navigate(route)
            }
        }
        composable(
            route = Application.SEARCH,
            exitTransition = {
                when (targetState.destination.route) {
                    BookInformationScreen.BOOK_INFO -> {
                        slideOutHorizontally(
                            targetOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("SearchBook", "exit") }
                    }

                    SearchScreen.FILTER -> {
                        slideOutVertically(
                            targetOffsetY = { -(it/it) },
                            animationSpec = tween(
                                durationMillis = 1000,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("SearchBook", "popExit") }
                    }

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    BookInformationScreen.BOOK_INFO ->
                        slideInHorizontally(
                            initialOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("SearchBook", "popEnter") }

                    SearchScreen.FILTER -> {
                        slideInVertically(
                            initialOffsetY = { 0 },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("Filter", "enter") }
                    }

                    else -> null
                }
            },
        ) {
            SearchBookScreen() { route ->
                navController.navigate(route)
            }
        }
        pickBookNavGraph(navController)
        filtersNavGraph(navController)
        booksNavGraph(navController)
        authNavGraph(navController)
    }
}

fun NavGraphBuilder.pickBookNavGraph(navController: NavHostController) {
    navigation(
        route = Application.PICK_BOOK,
        startDestination = UserBookScreen.PICK_BOOK
    ) {
        composable(
            route = UserBookScreen.PICK_BOOK
        ) {
            PickBookScreen(navController)
        }
    }
}

fun NavGraphBuilder.filtersNavGraph(navController: NavHostController) {
    val time = 800
    navigation(
        route = Application.FILTER,
        startDestination = SearchScreen.FILTER
    ) {
        composable(
            route = SearchScreen.FILTER,
            enterTransition = {
                when (initialState.destination.route) {
                    Application.SEARCH ->
                        slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("Filter", "enter") }

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Application.SEARCH ->
                        slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("Filter", "popExit") }

                    else -> null
                }
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
                when (initialState.destination.route) {
                    Application.SEARCH ->
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("BookInfo", "enter") }

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    BookInformationScreen.REVIEW -> {
                        slideOutHorizontally(
                            targetOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("BookInfo", "exit") }
                    }

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    BookInformationScreen.REVIEW ->
                        slideInHorizontally(
                            initialOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("BookInfo", "popEnter") }

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Application.SEARCH ->
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("BookInfo", "popExit") }

                    else -> null
                }
            }
        ) {
            BookInfoScreen(navController) { route ->
                navController.navigate(route)
            }
        }
        composable(
            route = BookInformationScreen.REVIEW,
            enterTransition = {
                when (initialState.destination.route) {
                    BookInformationScreen.BOOK_INFO ->
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AddReview", "enter") }

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    BookInformationScreen.BOOK_INFO ->
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AddReview", "popExit") }

                    else -> null
                }
            }
        ) {
            AddReviewScreen(navController = navController)
        }
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    val time = 500
    navigation(
        route = Application.AUTHORIZATION,
        startDestination = ProfileScreen.AUTHORIZATION
    ) {
        composable(
            route = ProfileScreen.AUTHORIZATION,
            enterTransition = {
                when (initialState.destination.route) {
                    Application.PROFILE ->
                        slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AUTHORIZATION", "enter") }

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    ProfileScreen.REGISTRATION -> {
                        slideOutVertically(
                            targetOffsetY = { it/it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AUTHORIZATION", "exit") }
                    }

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    ProfileScreen.REGISTRATION ->
                        slideInVertically(
                            initialOffsetY = { it/it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AUTHORIZATION", "popEnter") }

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Application.PROFILE ->
                        slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AUTHORIZATION", "popExit") }

                    else -> null
                }
            },
        ) {
            AuthScreen(navController = navController)
        }
        composable(
            route = ProfileScreen.REGISTRATION,
            enterTransition = {
                when (initialState.destination.route) {
                    ProfileScreen.AUTHORIZATION ->
                        slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("REGISTRATION", "enter") }

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    ProfileScreen.AUTHORIZATION ->
                        slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("REGISTRATION", "popExit") }

                    else -> null
                }
            },
        ) {
            RegScreen(navController = navController)
        }
    }
}
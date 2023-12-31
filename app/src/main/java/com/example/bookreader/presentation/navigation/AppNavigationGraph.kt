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
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.bookreader.presentation.AddReviewScreen.AddReviewScreen
import com.example.bookreader.presentation.AuthScreen.AuthScreen
import com.example.bookreader.presentation.BookInfoScreen.BookInfoScreen
import com.example.bookreader.presentation.FilterScreen.Filter
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
            exitTransition = {
                when (targetState.destination.route) {
                    Application.PICK_BOOK + "/{bookId}" -> {
                        slideOutHorizontally(
                            targetOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("USER_BOOK", "exit") }
                    }

                    Application.AUTHORIZATION -> {
                        slideOutVertically(
                            targetOffsetY = { it / it },
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
                    Application.PICK_BOOK + "/{bookId}" ->
                        slideInHorizontally(
                            initialOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("USER_BOOK", "popEnter") }

                    Application.AUTHORIZATION ->
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
            UserBookScreen() { route ->
                Log.d("Навигация", "${route}")
                navController.navigate(route)
            }
        }
        composable(
            route = Application.PROFILE,
            exitTransition = {
                when (targetState.destination.route) {
                    Application.AUTHORIZATION -> {
                        slideOutVertically(
                            targetOffsetY = { it / it },
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
                    Application.AUTHORIZATION ->
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
                    Application.BOOK_INFO + "/{bookId}" -> {
                        slideOutHorizontally(
                            targetOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("SearchBook", "exit") }
                    }

                    Application.FILTER + "/{filter}" -> {
                        slideOutVertically(
                            targetOffsetY = { -(it / it) },
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
                    Application.BOOK_INFO + "/{bookId}" ->
                        slideInHorizontally(
                            initialOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("SearchBook", "popEnter") }

                    Application.FILTER + "/{filter}" -> {
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
        ) { entry ->
            val filter = entry.savedStateHandle.get<Filter>("filter")
            Log.d("Пришло из фильтра", "$filter")
            SearchBookScreen(filter) { route ->
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("filter", filter)
                Log.d("Навигация", "${route}")
                navController.navigate(route)
            }
        }
        composable(
            route = Application.PICK_BOOK + "/{bookId}",
            enterTransition = {
                when (initialState.destination.route) {
                    Application.USER_BOOK ->
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("PICK_BOOK", "enter") }

                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    Application.USER_BOOK ->
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("PICK_BOOK", "popExit") }

                    else -> null
                }
            }
        ) {
            PickBookScreen(navController)
        }
        composable(
            route = Application.FILTER + "/{filter}",
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
        ) { entry ->
            val filterCur =
                navController.previousBackStackEntry?.savedStateHandle?.get<Filter>("filter")
            Log.d("Пришло из поиска", "$filterCur")
            FilterScreen(filterCur, navController = navController) { filter ->
                Log.d("Из навигации фильтр", "$filter")
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("filter", filter)
                navController.popBackStack()
            }
        }
        composable(
            route = Application.BOOK_INFO + "/{bookId}",
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
                    Application.REVIEW + "/{bookId}" -> {
                        slideOutHorizontally(
                            targetOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("BookInfo", "exit") }
                    }

                    Application.AUTHORIZATION -> {
                        slideOutVertically(
                            targetOffsetY = { it / it },
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
                    Application.REVIEW + "/{bookId}" ->
                        slideInHorizontally(
                            initialOffsetX = { -(it / 2) },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("BookInfo", "popEnter") }

                    Application.AUTHORIZATION ->
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
            BookInfoScreen(
                navController = navController,
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable(
            route = Application.REVIEW  + "/{bookId}",
            enterTransition = {
                when (initialState.destination.route) {
                    Application.BOOK_INFO + "/{bookId}" ->
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
                    Application.BOOK_INFO + "/{bookId}" ->
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
            AddReviewScreen(navController = navController) {
                navController.popBackStack()
            }
        }
        composable(
            route = Application.AUTHORIZATION,
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

                    Application.BOOK_INFO + "/{bookId}" ->
                        slideInVertically(
                            initialOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AUTHORIZATION", "enter") }

                    Application.USER_BOOK ->
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
                    Application.REGISTRATION -> {
                        slideOutVertically(
                            targetOffsetY = { it / it },
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
                    Application.REGISTRATION ->
                        slideInVertically(
                            initialOffsetY = { it / it },
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

                    Application.BOOK_INFO + "/{bookId}" ->
                        slideOutVertically(
                            targetOffsetY = { -it },
                            animationSpec = tween(
                                durationMillis = time,
                                easing = FastOutSlowInEasing
                            )
                        ).apply { Log.d("AUTHORIZATION", "popExit") }

                    Application.USER_BOOK ->
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
            AuthScreen(
                navController = navController,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(route) {
                            inclusive = false
                        }
                    }
                },
                onPopBackStack = { navController.popBackStack() }
            )
        }
        composable(
            route = Application.REGISTRATION,
            enterTransition = {
                when (initialState.destination.route) {
                    Application.AUTHORIZATION ->
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
                    Application.AUTHORIZATION ->
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
            RegScreen(navController = navController) {
                navController.popBackStack()
            }
        }
    }
}
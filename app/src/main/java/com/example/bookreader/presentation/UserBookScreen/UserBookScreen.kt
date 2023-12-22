package com.example.bookreader.presentation.UserBookScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bookreader.presentation.UserBookScreen.resourses.ActuallyBookCardTop
import com.example.bookreader.presentation.UserBookScreen.resourses.UserListBook
import com.example.bookreader.presentation.utils.UiEvent


@Composable
fun UserBookScreen(
    viewModel: UserBookViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    onNavigate(uiEvent.route)
                }

                else -> {}
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(UserBookEvent.OnLoad)
    }

    val partUser = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.user) {
        partUser.value = viewModel.user != null
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            ActuallyBookCardTop(
                viewModel = viewModel,
                partUser = partUser,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
            )
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                )
            } else {
                UserListBook(
                    partUser = partUser,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            bottom = 5.dp
                        )
                ) {
                    onNavigate(it)
                }
            }

        }
    }
}
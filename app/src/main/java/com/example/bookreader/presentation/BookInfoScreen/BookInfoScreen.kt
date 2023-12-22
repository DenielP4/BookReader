package com.example.bookreader.presentation.BookInfoScreen

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import com.example.bookreader.presentation.BookInfoScreen.dialog.WarningDialog
import com.example.bookreader.presentation.BookInfoScreen.resourses.BookCardTop
import com.example.bookreader.presentation.BookInfoScreen.resourses.BookTableInfo
import com.example.bookreader.presentation.UserBookScreen.UserBookEvent
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.utils.UiEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookInfoScreen(
    navController: NavController,
    viewModel: BookInfoViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    onNavigate(uiEvent.route)
                }

                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(BookInfoEvent.OnLoad)
    }

    LaunchedEffect(true) {
        viewModel.onEvent(BookInfoEvent.OnLoadBook)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = BlueDark,
                    modifier = Modifier.padding(bottom = 30.dp),
                    contentColor = Color.White
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                )
            } else {
                Column {
                    BookCardTop(
                        book = viewModel.book!!,
                        navController,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.45f)
                    )
                    BookTableInfo(
                        viewModel = viewModel,
                        book = viewModel.book!!,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        onNavigate(it)
                    }
                }
            }
        }
    }
    WarningDialog(dialogController = viewModel)
}



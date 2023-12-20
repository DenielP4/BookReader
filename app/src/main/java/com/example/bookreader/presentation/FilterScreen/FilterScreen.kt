package com.example.bookreader.presentation.FilterScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookreader.presentation.FilterScreen.resourses.MainFilterSection
import com.example.bookreader.presentation.FilterScreen.resourses.TopFilterSection
import com.example.bookreader.presentation.SearchBookScreen.SearchBookEvent
import com.example.bookreader.presentation.utils.UiEvent


@Composable
fun FilterScreen(
    filter: Filter?,
    viewModel: FilterViewModel = hiltViewModel(),
    navController: NavController,
    onPopBackStack: (Filter) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack(viewModel.filter)
                }

                else -> {}
            }
        }
    }

    if (filter != null) {
        viewModel.filter = filter
    }

    LaunchedEffect(viewModel.filter) {
        if (viewModel.filter != null) {
            viewModel.onEvent(FilterEvent.OnChangeFilter(viewModel.filter))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            TopFilterSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f)

            )
            MainFilterSection(
                viewModel = viewModel,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}






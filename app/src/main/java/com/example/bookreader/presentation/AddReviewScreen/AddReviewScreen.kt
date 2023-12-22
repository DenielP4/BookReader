package com.example.bookreader.presentation.AddReviewScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookreader.presentation.AddReviewScreen.resourses.BottomSectionReview
import com.example.bookreader.presentation.AddReviewScreen.resourses.CenterSectionReview
import com.example.bookreader.presentation.AddReviewScreen.resourses.TopSectionReview
import com.example.bookreader.presentation.UserBookScreen.UserBookEvent
import com.example.bookreader.presentation.utils.UiEvent


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddReviewScreen(
    navController: NavController,
    viewModel: AddReviewViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }

                else -> {}
            }
        }
    }

//    var rating by remember {
//        mutableFloatStateOf(0f)
//    }


    LaunchedEffect(true) {
        viewModel.onEvent(AddReviewEvent.OnLoad)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            TopSectionReview(
                viewModel = viewModel,
                navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
            ) { rating ->
                viewModel.onEvent(AddReviewEvent.OnReviewChangeRating(rating))
            }
            CenterSectionReview(
                viewModel = viewModel,
                modifier = Modifier
                    .weight(1f)
            )
            BottomSectionReview(
                viewModel = viewModel,
                ratingCurrent = viewModel.ratingChange.floatValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
            )
        }
    }
}
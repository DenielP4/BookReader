package com.example.bookreader.presentation.FilterScreen.resourses

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookreader.presentation.FilterScreen.FilterEvent
import com.example.bookreader.presentation.FilterScreen.FilterViewModel
import com.example.bookreader.presentation.ui.theme.Orange


@SuppressLint("UnrememberedMutableState")
@Composable
fun RatingSection(
    viewModel: FilterViewModel,
    modifier: Modifier = Modifier
) {
    val ratingTab = remember {
        mutableStateListOf(
            RatingToggleInfo(
                isChecked = false,
                rating = 5
            ),
            RatingToggleInfo(
                isChecked = false,
                rating = 4
            ),
            RatingToggleInfo(
                isChecked = false,
                rating = 3
            ),
            RatingToggleInfo(
                isChecked = false,
                rating = 2
            ),
            RatingToggleInfo(
                isChecked = false,
                rating = 1
            )
        )
    }

    var curentRating = mutableStateOf<List<Int>>(listOf())

    var triState by remember {
        mutableStateOf(ToggleableState.Indeterminate)
    }
    val toggleTriState = {
        viewModel.triState = when (viewModel.triState) {
            ToggleableState.Indeterminate -> ToggleableState.On
            ToggleableState.On -> ToggleableState.Off
            else -> ToggleableState.On
        }
        viewModel.ratingTab.indices.forEach { index ->
            viewModel.ratingTab[index] = viewModel.ratingTab[index].copy(
                isChecked = viewModel.triState == ToggleableState.On
            )
        }
        viewModel.onEvent(FilterEvent.OnChangeAllRating(viewModel.ratingTab.toList()))
    }

    Column(
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    toggleTriState()
                }
                .padding(end = 16.dp)
        ) {
            TriStateCheckbox(
                state = viewModel.triState,
                onClick = toggleTriState
            )
            Text(
                text = "РЕЙТИНГ:",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        viewModel.ratingTab.forEachIndexed { index, rating ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 32.dp)
                    .clickable {
                        viewModel.ratingTab[index] = rating.copy(
                            isChecked = !rating.isChecked
                        )
                        viewModel.triState = ToggleableState.Indeterminate
                        viewModel.onEvent(FilterEvent.OnChangeAllRating(viewModel.ratingTab.toList()))
                    }
                    .padding(end = 16.dp)
            ) {
                Checkbox(
                    checked = rating.isChecked,
                    onCheckedChange = { isChecked ->
                        viewModel.ratingTab[index] = rating.copy(
                            isChecked = isChecked
                        )
                        viewModel.triState = ToggleableState.Indeterminate
                        viewModel.onEvent(FilterEvent.OnChangeAllRating(viewModel.ratingTab.toList()))
                    }
                )
                Row() {
                    repeat(rating.rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Рейтинг",
                            tint = Orange
                        )
                    }
                    val fold = 5 - rating.rating
                    if (fold > 0) {
                        repeat(fold) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Рейтинг",
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
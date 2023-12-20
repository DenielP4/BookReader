package com.example.bookreader.presentation.SearchBookScreen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bookreader.R
import com.example.bookreader.presentation.AuthScreen.resourses.DownAuth
import com.example.bookreader.presentation.AuthScreen.resourses.UpAuth
import com.example.bookreader.presentation.FilterScreen.Filter
import com.example.bookreader.presentation.SearchBookScreen.resourses.BookCardSelection
import com.example.bookreader.presentation.SearchBookScreen.resourses.SearchCard
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.ui.theme.Border
import com.example.bookreader.presentation.ui.theme.Orange
import com.example.bookreader.presentation.ui.theme.TextLight
import com.example.bookreader.presentation.utils.UiEvent

@Composable
fun SearchBookScreen(
    filter: Filter?,
    viewModel: SearchBookViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    Log.d("Из лаунча", "${uiEvent.route}")
                    onNavigate(uiEvent.route)
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
            viewModel.onEvent(SearchBookEvent.OnChangeFilter(viewModel.filter!!))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            SearchCard(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.16f)
                    .background(BlueLight)
            ){
                onNavigate(it)
            }
            BookCardSelection(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 5.dp
                    )
            ){
                onNavigate(it)
            }
        }
    }
}
package com.example.bookreader.presentation.BookInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.bookreader.presentation.BookInfoScreen.resourses.BookCardTop
import com.example.bookreader.presentation.BookInfoScreen.resourses.BookTableInfo


@Composable
fun BookInfoScreen(
    navController: NavController,
    onNavigate: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            BookCardTop(
                navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
            )
            BookTableInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                onNavigate(it)
            }
        }
    }
}



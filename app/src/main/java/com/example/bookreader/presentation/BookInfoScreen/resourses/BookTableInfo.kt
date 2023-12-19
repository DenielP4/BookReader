package com.example.bookreader.presentation.BookInfoScreen.resourses

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bookreader.domain.models.BookInfo
import com.example.bookreader.presentation.ui.theme.BlueLight

data class TabItem(
    val title: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookTableInfo(
    book: BookInfo,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val tabItems = listOf(
        TabItem(
            title = "Описание"
        ),
        TabItem(
            title = "Отзывы"
        )
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        selectedTabIndex = pagerState.currentPage
    }
    Column(
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Black,
            contentColor = BlueLight
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = tabItem.title,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (index) {
                    0 -> {
                        DescriptionBookSelection(
                            book = book,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    1 -> {
                        ReviewsBookSelection(
                            book = book,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            onNavigate(it)
                        }
                    }
                }
            }
        }
    }
}
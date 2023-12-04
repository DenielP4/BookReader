package com.example.bookreader.presentation.SearchBookScreen.resourses

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookreader.R
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.Border
import com.example.bookreader.presentation.utils.Routes

@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        SearchItem(
            modifier = Modifier
                .fillMaxWidth()
        ){
            onNavigate(it)
        }
        FilterItem(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchItem(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val searchText = remember {
        mutableStateOf("")
    }
//    val isActiv = remember {
//        mutableStateOf(false)
//    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.9f),
            query = searchText.value,
            onQueryChange = { text ->
                searchText.value = text
            },
            onSearch = { text ->
//                isActiv.value = false
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color.White
            ),
            active = false,
            onActiveChange = {
//                isActiv.value = it
            },
            placeholder = {
                Text(
                    text = "Название книги или автор",
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск",
                    tint = Color.Gray
                )
            },
            shape = RoundedCornerShape(5.dp)
        ) {}
        IconButton(
            onClick = { onNavigate(Routes.FILTER) },
            colors = IconButtonDefaults.iconButtonColors(BlueDark),
            modifier = Modifier
                .padding(end = 5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Фильтер",
                tint = Color.White,
                modifier = Modifier.size(40.dp).padding(5.dp)
            )
        }
    }

}

data class Filter(
    val text: String,
    val onTurn: Boolean
)

@Composable
fun FilterItem(
    modifier: Modifier = Modifier
) {
    val filterList = remember {
        mutableStateListOf(
            Filter(
                "ХОРРОР",
                false
            ),
            Filter(
                "КОМЕДИЯ",
                false
            ),
            Filter(
                "ДЕТЕКТИВ",
                false
            ),
            Filter(
                "ФЭНТЕЗИ",
                false
            ),
            Filter(
                "ФАНФИК",
                false
            ),
            Filter(
                "СКАЗКИ",
                false
            ),
        )
    }
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        items(filterList) { filter ->
            Log.d("Filter", "$filter")
            BoxFilter(
                filter = filter,
                filterList = filterList
            )
        }
    }
}


@Composable
fun BoxFilter(
    filter: Filter,
    filterList: SnapshotStateList<Filter>
) {
    Button(
        onClick = {
            filterList.replaceAll {
                it.copy(
                    onTurn = it.text == filter.text
                )
            }
        },
        colors = ButtonDefaults.buttonColors(BlueDark),
        border = BorderStroke(
            width = 2.dp,
            color = if (filter.onTurn) Border else BlueDark
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 2.dp),
            text = filter.text,
            color = Color.White,
            fontSize = 15.sp
        )
    }
}
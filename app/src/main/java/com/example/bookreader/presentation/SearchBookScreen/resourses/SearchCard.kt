package com.example.bookreader.presentation.SearchBookScreen.resourses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookreader.R
import com.example.bookreader.presentation.SearchBookScreen.FilterGenre
import com.example.bookreader.presentation.SearchBookScreen.SearchBookEvent
import com.example.bookreader.presentation.SearchBookScreen.SearchBookViewModel
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.Border
import com.example.bookreader.presentation.utils.Application
import com.example.bookreader.presentation.utils.Routes

@Composable
fun SearchCard(
    viewModel: SearchBookViewModel,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        SearchItem(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
        ) { event ->
            viewModel.onEvent(event)
        }
        FilterItem(
            viewModel = viewModel,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchItem(
    viewModel: SearchBookViewModel,
    modifier: Modifier = Modifier,
    onEvent: (SearchBookEvent) -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier.padding(horizontal = 5.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .padding(end = 5.dp)
                .weight(0.85f)
                .focusRequester(focusRequester),
            value = viewModel.searchText.value,
            onValueChange = { text ->
                viewModel.onEvent(SearchBookEvent.OnTextSearchChange(text))
            },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            singleLine = true,
            shape = RoundedCornerShape(5.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            ),
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
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        viewModel.onEvent(SearchBookEvent.OnClickDeleteSearchText)
                        keyboard?.hide()
                        focusManager.clearFocus()
                    }
                )
            },
            isError = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )

        )
        IconButton(
            onClick = {
                onEvent(
                    SearchBookEvent.OnShowFilterScreen(
                        Application.FILTER + "/${viewModel.filter}"
                    )
                )
            },
            modifier = Modifier
                .padding(top = 3.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BlueDark)
                .wrapContentSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Фильтер",
                tint = Color.White,
                modifier = Modifier
                    .size(45.dp)
                    .padding(5.dp)
            )
        }
    }

}


@Composable
fun FilterItem(
    viewModel: SearchBookViewModel,
    modifier: Modifier = Modifier
) {

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        items(viewModel.filterList) { filter ->
            BoxFilter(
                filter = filter,
            ) {
                viewModel.onEvent(SearchBookEvent.OnFilterGenre(filter.text))
            }
        }
    }
}


@Composable
fun BoxFilter(
    filter: FilterGenre,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
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
package com.example.bookreader.presentation.UserBookScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.example.bookreader.R
import com.example.bookreader.presentation.BookInfoScreen.resourses.BookCardTop
import com.example.bookreader.presentation.BookInfoScreen.resourses.BookTableInfo
import com.example.bookreader.presentation.SearchBookScreen.resourses.BookCardItem
import com.example.bookreader.presentation.UserBookScreen.resourses.ActuallyBookCardTop
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.ui.theme.GrayLight
import com.example.bookreader.presentation.ui.theme.Orange

@Preview
@Composable
fun UserBookScreen(
//    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            ActuallyBookCardTop(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f)
            )
            UserListBook(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}

data class Menu(
    val text: String,
    val isChecked: Boolean
)

@Composable
fun UserListBook(
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val sorting = createRefFor("sorting")
        val listOfBooks = createRefFor("listOfBooks")

        constrain(sorting) {
            top.linkTo(parent.top, 10.dp)
            start.linkTo(parent.start, 10.dp)
        }
        constrain(listOfBooks) {
            top.linkTo(sorting.bottom, 10.dp)
            start.linkTo(sorting.start)
        }
    }

    val sortList = arrayOf(
        Menu(
            text = "ПО АЛФАВИТУ",
            isChecked = true
        ),
        Menu(
            text = "ПО АВТОРУ",
            isChecked = false
        ),
        Menu(
            text = "ПО ПРОГРЕССУ",
            isChecked = false
        )
    )
    var selectedText by remember {
        mutableStateOf(sortList[0])
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.layoutId("sorting"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "СОРТИРОВКА",
                color = GrayLight,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(5.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    expanded = !expanded
                }
            ) {
                Text(
                    text = selectedText.text,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "Сортировка",
                    tint = Color.White
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    sortList.forEach { item ->
                        DropdownMenuItem(
                            text = {
                                Text(text = item.text)
                            },
                            leadingIcon = {
                                if (item.isChecked)
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Выбрано",
                                        tint = Color.Black
                                    )
                            },
                            onClick = {
                                expanded = false
                                selectedText = item
                                sortList.indices.forEach { index ->
                                    sortList[index] = sortList[index].copy(
                                        isChecked = selectedText.text == sortList[index].text
                                    )
                                }
                            }
                        )
                        if (item != sortList[sortList.size-1]) {
                            Divider(
                                color = GrayLight
                            )
                        }
                    }
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .layoutId("listOfBooks")
                .padding(bottom = 100.dp)
        ) {
            items(5) {
                AddedBookCardItem(
                    modifier = Modifier
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AddedBookCardItem(
    modifier: Modifier = Modifier
) {

    val constrains = ConstraintSet {
        val imageBook = createRefFor("imageBook")
        val nameBook = createRefFor("nameBook")
        val nameAuthor = createRefFor("nameAuthor")
        val progressBook = createRefFor("progressBook")

        constrain(imageBook) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }

        constrain(nameBook) {
            top.linkTo(imageBook.top)
            start.linkTo(imageBook.end, 20.dp)
        }
        constrain(nameAuthor) {
            top.linkTo(nameBook.bottom, 7.dp)
            start.linkTo(nameBook.start)
        }

        constrain(progressBook) {
            bottom.linkTo(imageBook.bottom)
            start.linkTo(nameAuthor.start)
        }
    }
    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier.padding(vertical = 10.dp)
    ) {

        Card(
            modifier = Modifier
                .size(width = 150.dp, height = 221.dp)
                .clip(RoundedCornerShape(5.dp))
                .layoutId("imageBook")
        ) {
            Image(
                painter = painterResource(id = R.drawable.hp),
                contentDescription = "Картинка",
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp)),
                contentScale = ContentScale.FillWidth
            )
        }

        Text(
            text = "Название книги",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("nameBook")
        )
        Text(
            text = "Автор книги",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier.layoutId("nameAuthor")
        )

        Text(
            text = "50%",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("progressBook")
        )
    }
}
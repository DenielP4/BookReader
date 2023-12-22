package com.example.bookreader.presentation.UserBookScreen.resourses

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.bookreader.R
import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.domain.models.UserBook
import com.example.bookreader.presentation.UserBookScreen.UserBookEvent
import com.example.bookreader.presentation.UserBookScreen.UserBookViewModel
import com.example.bookreader.presentation.ui.theme.BlackLight
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.GrayLight
import com.example.bookreader.presentation.utils.Application
import kotlin.math.roundToInt

data class Menu(
    val text: String,
    val isChecked: Boolean
)

@Composable
fun UserListBook(
    viewModel: UserBookViewModel,
    partUser: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val constrains = ConstraintSet {
        val sorting = createRefFor("sorting")
        val listOfBooks = createRefFor("listOfBooks")

        constrain(sorting) {
            top.linkTo(parent.top, 10.dp)
            start.linkTo(parent.start)
        }
        constrain(listOfBooks) {
            top.linkTo(sorting.bottom, 10.dp)
            start.linkTo(sorting.start)
        }
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    if (partUser.value) {
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
                        text = viewModel.selectedText.text,
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
                        viewModel.sortList.forEach { item ->
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
                                    viewModel.onEvent(UserBookEvent.OnChangeSort(item))
                                }
                            )
                            if (item != viewModel.sortList[viewModel.sortList.size - 1]) {
                                Divider(
                                    color = GrayLight
                                )
                            }
                        }
                    }
                }
            }
            if (viewModel.loadUserBook.value) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .layoutId("listOfBooks")
                        .padding(bottom = 50.dp)
                ) {
                    items(viewModel.books.value) { book ->
                        AddedBookCardItem(
                            viewModel = viewModel,
                            book = book,
                            modifier = Modifier
                                .fillMaxHeight(0.2f)
                                .fillMaxWidth()
                        ) {
                            onNavigate(it)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddedBookCardItem(
    viewModel: UserBookViewModel,
    book: UserBook,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val squareSize = 100.dp
    val swipeAbleState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1)

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

    val constrainsSwipe = ConstraintSet {
        val delete = createRefFor("delete")
        constrain(delete) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, 10.dp)
        }
    }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(BlackLight)
            .clickable {
                viewModel.onEvent(UserBookEvent.OnClickBook(
                    Application.PICK_BOOK + "/${book.bookId}"
                ))
                Log.d("UserList", "ТУТ")
            }
            .swipeable(
                state = swipeAbleState,
                anchors = anchors,
                thresholds = { _, _ ->
                    FractionalThreshold(0.3f)
                },
                orientation = Orientation.Horizontal
            ),
        contentAlignment = Alignment.Center
    ) {
        ConstraintLayout(
            constraintSet = constrainsSwipe,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BlueDark)
                    .layoutId("delete")
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        swipeAbleState.offset.value.roundToInt(), 0
                    )
                }
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 10.dp)
                .align(Alignment.CenterStart)
        ) {
            ConstraintLayout(
                constraintSet = constrains,
            ) {
                Card(
                    modifier = Modifier
                        .size(width = 150.dp, height = 221.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .layoutId("imageBook")
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(book.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = book.name,
                        loading = {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primary, modifier = Modifier.scale(0.5F)
                            )
                        },
                        success = { success ->
                            SubcomposeAsyncImageContent()
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp)).size(width = 150.dp, height = 221.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = book.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.layoutId("nameBook")
                )
                Text(
                    text = book.author,
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
    }
}
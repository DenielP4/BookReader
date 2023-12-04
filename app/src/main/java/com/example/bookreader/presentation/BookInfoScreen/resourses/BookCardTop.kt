package com.example.bookreader.presentation.BookInfoScreen.resourses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.example.bookreader.R
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.ui.theme.Orange

@Composable
fun BookCardTop(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var rating = 4 // рейтинг книги
    val constrains = ConstraintSet {
        val backArrow = createRefFor("back")
        val imageBook = createRefFor("imageBook")
        val nameBook = createRefFor("nameBook")
        val nameAuthor = createRefFor("nameAuthor")
        val ratingBook = createRefFor("ratingBook")
        val addButton = createRefFor("addButton")

        constrain(backArrow) {
            top.linkTo(parent.top, 5.dp)
            start.linkTo(parent.start, 5.dp)
        }

        constrain(imageBook) {
            top.linkTo(backArrow.bottom, 15.dp)
            bottom.linkTo(parent.bottom, (-10).dp)
            start.linkTo(backArrow.start, 10.dp)
        }

        constrain(nameBook) {
            top.linkTo(imageBook.top)
            start.linkTo(imageBook.end, 10.dp)
        }
        constrain(nameAuthor) {
            top.linkTo(nameBook.bottom, 7.dp)
            start.linkTo(nameBook.start)
        }
        constrain(ratingBook) {
            top.linkTo(nameAuthor.bottom, 40.dp)
            start.linkTo(nameAuthor.start)
        }
        constrain(addButton) {
            bottom.linkTo(imageBook.bottom, (-5).dp)
            start.linkTo(imageBook.end, 10.dp)
            end.linkTo(parent.end, 10.dp)
        }

    }
    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = 30.dp,
                bottomStart = 30.dp,
            ),
            colors = CardDefaults.cardColors(BlueLight),
            content = {}
        )
        IconButton(
            modifier = Modifier.layoutId("backArrow"),
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Card(
            modifier = Modifier
                .size(width = 200.dp, height = 285.dp)
                .clip(RoundedCornerShape(5.dp))
                .layoutId("imageBook"),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(3.dp, color = BlueDark)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hp),
                contentDescription = "Картинка",
                contentScale = ContentScale.FillWidth
            )
        }

        Text(
            text = "Название книги",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            softWrap = true,
            modifier = Modifier.layoutId("nameBook")
        )
        Text(
            text = "Автор книги",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.layoutId("nameAuthor")
        )
        Box(
            modifier = Modifier.layoutId("ratingBook")
        ) {
            Column {
                Text(
                    text = "Рейтинг книги:",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row() {
                    repeat(rating) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Рейтинг",
                            tint = Orange,
                            modifier = Modifier.size(35.dp)
                        )
                    }
                    val fold = 5 - rating
                    if (fold > 0)
                        repeat(fold) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Рейтинг",
                                tint = Color.Gray,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                }
            }
        }
        Button(
            modifier = Modifier.layoutId("addButton"),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(BlueDark),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 5.dp
            )
        ) {
            Text(
                text = "ДОБАВИТЬ",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
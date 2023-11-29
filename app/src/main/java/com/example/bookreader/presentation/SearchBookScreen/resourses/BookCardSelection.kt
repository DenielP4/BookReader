package com.example.bookreader.presentation.SearchBookScreen.resourses

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.bookreader.R
import com.example.bookreader.presentation.ui.theme.Orange

@Composable
fun BookCardSelection(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(5) {
            BookCardItem(
                modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview()
@Composable
fun BookCardItem(
    modifier: Modifier = Modifier
) {
    var rating = 4 // рейтинг книги
    var look = 20 // количество просмотров

    val constrains = ConstraintSet {
        val imageBook = createRefFor("imageBook")
        val nameBook = createRefFor("nameBook")
        val nameAuthor = createRefFor("nameAuthor")
        val ratingBook = createRefFor("ratingBook")
        val lookCount = createRefFor("lookCount")

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
        constrain(ratingBook) {
            top.linkTo(nameAuthor.bottom, 7.dp)
            start.linkTo(nameBook.start)
        }
        constrain(lookCount) {
            bottom.linkTo(imageBook.bottom)
            start.linkTo(nameBook.start)
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
        Box(
            modifier = Modifier.layoutId("ratingBook")
        ) {
            Row() {
                repeat(rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Рейтинг",
                        tint = Orange
                    )
                }
                val fold = 5 - rating
                if (fold > 0)
                    repeat(fold) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Рейтинг",
                            tint = Color.Gray
                        )
                    }
            }
        }
        Row(
            modifier = Modifier.layoutId("lookCount")
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_eyes),
                contentDescription = "Рейтинг",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = look.toString(),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
package com.example.bookreader.presentation.BookInfoScreen.resourses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.bookreader.R
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.GrayLight
import com.example.bookreader.presentation.ui.theme.Orange
import com.example.bookreader.presentation.ui.theme.GrayDark
import com.example.bookreader.presentation.utils.BookInformationScreen

@Composable
fun ReviewsBookSelection(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val constrains = ConstraintSet {
        val reviewList = createRefFor("reviewList")
        val addReviewButton = createRefFor("addReviewButton")

        constrain(reviewList) {
            top.linkTo(parent.top, 10.dp)
        }

        constrain(addReviewButton) {
            start.linkTo(reviewList.start)
            end.linkTo(reviewList.end)
            bottom.linkTo(parent.bottom, 2.dp)
        }
    }

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .layoutId("reviewList").padding(bottom = 70.dp)
        ) {
            items(5) {
                ReviewUser(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )
            }
        }
        Button(
            modifier = Modifier.layoutId("addReviewButton"),
            onClick = { onNavigate(BookInformationScreen.REVIEW) },
            colors = ButtonDefaults.buttonColors(BlueDark),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(
                horizontal = 10.dp,
                vertical = 5.dp
            )
        ) {
            Text(
                text = "ОСТАВИТЬ ОТЗЫВ",
                fontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun ReviewUser(
    modifier: Modifier = Modifier
) {
    var rating = 4 // рейтинг книги

    val constrains = ConstraintSet {
        val icon = createRefFor("icon")
        val userName = createRefFor("userName")
        val date = createRefFor("date")
        val reviewRating = createRefFor("reviewRating")
        val review = createRefFor("review")

        constrain(icon) {
            top.linkTo(parent.top, 5.dp)
            start.linkTo(parent.start, 5.dp)
        }

        constrain(userName) {
            top.linkTo(icon.top)
            start.linkTo(icon.end, 10.dp)
        }

        constrain(date) {
            top.linkTo(userName.bottom)
            start.linkTo(userName.start)
            bottom.linkTo(icon.bottom)
        }

        constrain(reviewRating) {
            top.linkTo(parent.top, 10.dp)
            end.linkTo(parent.end, 5.dp)
        }

        constrain(review) {
            top.linkTo(icon.bottom, 5.dp)
            start.linkTo(icon.start,5.dp)
            end.linkTo(parent.end, 10.dp)
            bottom.linkTo(parent.bottom, 10.dp)
        }
    }

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier.background(GrayDark)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = "Иконка пользователя",
            tint = GrayLight,
            modifier = Modifier.layoutId("icon").size(40.dp)
        )
        Text(
            text = "Ксения",
            fontSize = 17.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.layoutId("userName")
        )
        Text(
            text = "26.11.2023, 17:12",
            fontSize = 15.sp,
            color = GrayLight,
            modifier = Modifier.layoutId("date")
        )
        Text(
            text = "Хорошая книга, всем советую. Респект. 300 страниц за 1 секунду. Желаю Вам хорошего чтива!",
            fontSize = 15.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Justify,
            lineHeight = 14.sp,
            modifier = Modifier.layoutId("review").padding(horizontal = 10.dp)
        )
        Box(
            modifier = Modifier.layoutId("reviewRating")
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
    }
}
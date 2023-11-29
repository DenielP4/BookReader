package com.example.bookreader.presentation.ProfileScreen.resourses

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Preview()
@Composable
fun BottomStatisticUser(
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val userName = createRefFor("userName")
        val bookStart = createRefFor("bookStart")
        val bookFinish = createRefFor("bookFinish")
        val dateAuth = createRefFor("dateAuth")

        constrain(userName) {
            bottom.linkTo(parent.top, (-150).dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(bookStart) {
            top.linkTo(userName.bottom, 20.dp)
            start.linkTo(parent.start, 50.dp)
        }
        constrain(bookFinish) {
            top.linkTo(userName.bottom, 20.dp)
            end.linkTo(parent.end, 50.dp)
        }

        constrain(dateAuth) {
            top.linkTo(bookStart.bottom, 100.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        Text(
            text = "Иванов Иван",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .layoutId("userName")
        )
        Row(
            modifier = Modifier
                .layoutId("bookStart")
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_book_read),
                contentDescription = "Book Read",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "7",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }
        Row(
            modifier = Modifier
                .layoutId("bookFinish")
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_book),
                contentDescription = "Book",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "20",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }

        Text(
            text = "10.01.2024",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("dateAuth")
        )
    }
}
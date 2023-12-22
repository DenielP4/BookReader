package com.example.bookreader.presentation.UserBookScreen.resourses

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.bookreader.R
import com.example.bookreader.presentation.UserBookScreen.UserBookEvent
import com.example.bookreader.presentation.UserBookScreen.UserBookViewModel
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.utils.Application

@Composable
fun ActuallyBookCardTop(
    viewModel: UserBookViewModel,
    partUser: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val text = createRefFor("text")
        val imageBook = createRefFor("imageBook")
        val nameBook = createRefFor("nameBook")
        val nameAuthor = createRefFor("nameAuthor")
        val progressBook = createRefFor("progressBook")
        val warningText = createRefFor("warningText")
        val buttonAuth = createRefFor("buttonAuth")

        constrain(text) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 8.dp)
        }
        constrain(warningText) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(text.bottom, 70.dp)
        }
        constrain(buttonAuth) {
            start.linkTo(warningText.start)
            end.linkTo(warningText.end)
            top.linkTo(warningText.bottom, 20.dp)
        }
        constrain(imageBook) {
            top.linkTo(text.bottom, 10.dp)
            start.linkTo(parent.start, 10.dp)
        }
        constrain(nameBook) {
            top.linkTo(imageBook.top)
            start.linkTo(imageBook.end, 10.dp)
        }
        constrain(nameAuthor) {
            top.linkTo(nameBook.bottom, 7.dp)
            start.linkTo(nameBook.start)
        }
        constrain(progressBook) {
            top.linkTo(nameAuthor.bottom, 7.dp)
            start.linkTo(nameAuthor.start)
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
        Text(
            text = "МОЯ ПОЛКА",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("text")
        )
        if (partUser.value) {
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
            Text(
                text = "89%",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.layoutId("progressBook")
            )
        } else {
            Text(
                text = "Уважаемый пользователь! Здесь будут отображаться Ваши книги. Пожалуйста, авторизуйтесь!",
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.layoutId("warningText")
            )
            Button(
                modifier = Modifier.layoutId("buttonAuth"),
                onClick = {
                    viewModel.onEvent(UserBookEvent.OnClickAuth(Application.AUTHORIZATION))
                },
                colors = ButtonDefaults.buttonColors(BlueDark),
                shape = RoundedCornerShape(18.dp),
                contentPadding = PaddingValues(
                    horizontal = 20.dp,
                    vertical = 5.dp
                )
            ) {
                Text(
                    text = "ВОЙТИ",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
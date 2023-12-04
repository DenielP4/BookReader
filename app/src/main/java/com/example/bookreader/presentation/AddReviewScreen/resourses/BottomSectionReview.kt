package com.example.bookreader.presentation.AddReviewScreen.resourses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.Gray
import com.example.bookreader.presentation.ui.theme.GrayDark
import com.example.bookreader.presentation.ui.theme.GrayLight

@Composable
fun BottomSectionReview(
    ratingCurrent: Float,
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 10.dp)
        }

        constrain(text) {
            top.linkTo(button.bottom, 10.dp)
            start.linkTo(button.start)
            end.linkTo(button.end)
        }
    }

    var textOnButton by remember {
        mutableStateOf("ПОСТАВИТЬ ОЦЕНКУ ОТ 1 ДО 5")
    }
    var colorButton by remember {
        mutableStateOf(Gray)
    }
    LaunchedEffect(key1 = ratingCurrent) {
        if (ratingCurrent == 0f) {
            textOnButton = "ПОСТАВИТЬ ОЦЕНКУ ОТ 1 ДО 5"
            colorButton = Gray
        } else {
            textOnButton = "ОСТАВИТЬ ОТЗЫВ"
            colorButton = BlueDark
        }

    }

    val textHepl =
        "Оставляя отзыв, вы даёте согласие на использование данных отзыва на сторонних ресурсах"
    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp,
            ),
            colors = CardDefaults.cardColors(GrayDark),
            content = {}
        )
        Button(
            modifier = Modifier
                .layoutId("button")
                .width(350.dp),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(colorButton),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(
                horizontal = 25.dp,
                vertical = 15.dp
            )
        ) {
            Text(
                text = textOnButton,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
        Text(
            text = textHepl,
            color = GrayLight,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .layoutId("text")
                .padding(horizontal = 10.dp)
        )
    }
}
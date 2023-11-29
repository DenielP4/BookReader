package com.example.bookreader.presentation.AuthScreen.resourses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.bookreader.R
import com.example.bookreader.presentation.ui.theme.BlueLight

@Composable
fun UpAuth(
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val authText = createRefFor("authText")

        constrain(authText) {
            bottom.linkTo(parent.bottom, 5.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
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
            content = {},
            colors = CardDefaults.cardColors(BlueLight)
        )
        Text(
            text = "АВТОРИЗАЦИЯ",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            modifier = Modifier
                .layoutId("authText"),
        )
    }
}
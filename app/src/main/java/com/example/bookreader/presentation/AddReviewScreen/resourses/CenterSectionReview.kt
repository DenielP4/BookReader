package com.example.bookreader.presentation.AddReviewScreen.resourses

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.example.bookreader.presentation.ui.theme.GrayDark

@Composable
fun CenterSectionReview(
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val selection = createRefFor("selection")

        constrain(selection) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 20.dp)
        }

    }
    val constrainsInSelection = ConstraintSet {
        val text = createRefFor("text")
        val input = createRefFor("input")

        constrain(text) {
            start.linkTo(parent.start, 10.dp)
            top.linkTo(parent.top, 10.dp)
        }
        constrain(input) {
            top.linkTo(text.bottom, 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, 20.dp)
        }
    }

    var review by remember {
        mutableStateOf("")
    }

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        Card(
            colors = CardDefaults.cardColors(GrayDark),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .layoutId("selection")
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            ConstraintLayout(
                constraintSet = constrainsInSelection,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Комментарий",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.layoutId("text")
                )
                TextField(
                    modifier = Modifier
                        .layoutId("input")
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    value = review,
                    onValueChange = { review = it },
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Left
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(
                            text = "Оставьте Ваши впечатления",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
            }
        }
    }
}
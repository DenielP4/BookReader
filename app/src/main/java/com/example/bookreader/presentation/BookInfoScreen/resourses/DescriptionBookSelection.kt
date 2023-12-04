package com.example.bookreader.presentation.BookInfoScreen.resourses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun DescriptionBookSelection(
    modifier: Modifier = Modifier
) {
    val descriptionText =
        "Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание.Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание.Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание.Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание.Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание.Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание.Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание.Эта книга про то-то и то-то. Там есть гг и несколько второстепенных героев. А это самое худшее краткое описание."

    val constrains = ConstraintSet {
        val description = createRefFor("description")

        constrain(description) {
            top.linkTo(parent.top, 10.dp)
        }
    }

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .layoutId("description").padding(bottom = 20.dp)
        ) {
            item {
                Text(
                    text = descriptionText,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    softWrap = true,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                )
            }

        }

    }
}
package com.example.bookreader.presentation.FilterScreen.resourses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bookreader.presentation.ui.theme.BlueDark

@Composable
fun MainFilterSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("filter.json"))

    val constrains = ConstraintSet {
        val anim = createRefFor("anim")
        val nameBook = createRefFor("nameBook")
        val authorBook = createRefFor("authorBook")
        val ratingFilter = createRefFor("ratingFilter")
        val button = createRefFor("button")

        constrain(anim) {
            top.linkTo(parent.top, 10.dp)
            start.linkTo(parent.start, 10.dp)
            end.linkTo(parent.end, 10.dp)
        }
        constrain(nameBook) {
            top.linkTo(anim.bottom, 10.dp)
            start.linkTo(anim.start)
            end.linkTo(anim.end)
        }
        constrain(authorBook) {
            top.linkTo(nameBook.bottom, 10.dp)
            start.linkTo(nameBook.start)
            end.linkTo(nameBook.end)
        }
        constrain(ratingFilter) {
            top.linkTo(authorBook.bottom, 10.dp)
            start.linkTo(authorBook.start)
        }
        constrain(button) {
            bottom.linkTo(parent.bottom, 10.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    var name by remember {
        mutableStateOf("")
    }
    var author by remember {
        mutableStateOf("")
    }

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .layoutId("anim")
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(200.dp)
            )
        }
        TextField(
            modifier = Modifier.layoutId("nameBook"),
            value = name,
            onValueChange = { name = it },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White
            ),
            placeholder = {
                Text(text = "Название книги")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Book,
                    contentDescription = "Книга"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        name = ""
                    }
                )
            },
            isError = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        TextField(
            modifier = Modifier.layoutId("authorBook"),
            value = author,
            onValueChange = { author = it },
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Left
            ),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White
            ),
            placeholder = {
                Text(text = "Автор")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Автор"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        author = ""
                    }
                )
            },
            isError = false,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )
        RatingSection(modifier = Modifier.layoutId("ratingFilter"))
        Button(
            modifier = Modifier.layoutId("button"),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(BlueDark),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(
                horizontal = 20.dp,
                vertical = 15.dp
            )
        ) {
            Text(
                text = "ПРИМЕНИТЬ",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
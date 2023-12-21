package com.example.bookreader.presentation.ProfileScreen.resourses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.bookreader.presentation.AuthScreen.AuthEvent
import com.example.bookreader.presentation.ProfileScreen.ProfileEvent
import com.example.bookreader.presentation.ProfileScreen.ProfileViewModel
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.utils.Application


@Composable
fun BottomStatisticUser(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val userName = createRefFor("userName")
        val bookStart = createRefFor("bookStart")
        val bookFinish = createRefFor("bookFinish")
        val dateAuth = createRefFor("dateAuth")
        val buttonAuth = createRefFor("buttonAuth")
        val helpText = createRefFor("helpText")

        constrain(userName) {
            bottom.linkTo(parent.top, (-150).dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(helpText) {
            bottom.linkTo(parent.top, (-150).dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(buttonAuth) {
            top.linkTo(helpText.bottom, 20.dp)
            start.linkTo(helpText.start)
            end.linkTo(helpText.end)
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

    val partProfile = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(viewModel.user) {
        partProfile.value = viewModel.user != null
    }

    ConstraintLayout(
        constraintSet = constrains,
        modifier = modifier
    ) {
        if (partProfile.value) {
            Text(
                text = viewModel.user?.name!!,
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
                text = viewModel.user?.registerDate!!,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .layoutId("dateAuth")
            )
        } else {
            Text(
                text = "После авторизации здесь будет виден Ваш профиль",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .layoutId("helpText")
            )
            Button(
                modifier = Modifier.layoutId("buttonAuth"),
                onClick = {
                    viewModel.onEvent(ProfileEvent.OnClickAuth(Application.AUTHORIZATION))
                },
                colors = ButtonDefaults.buttonColors(BlueDark),
                shape = RoundedCornerShape(18.dp),
                contentPadding = PaddingValues(
                    horizontal = 75.dp,
                    vertical = 15.dp
                )
            ) {
                Text(
                    text = "Авторизуйтесь",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
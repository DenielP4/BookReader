package com.example.bookreader.presentation.ProfileScreen.resourses

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.example.bookreader.R
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.utils.ProfileScreen

@Composable
fun CardProfile(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit
) {
    val constrains = ConstraintSet {
        val image = createRefFor("image")
        val logOut = createRefFor("logOut")

        constrain(image) {
            bottom.linkTo(parent.bottom, (-100).dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(logOut) {
            top.linkTo(parent.top, 5.dp)
            end.linkTo(parent.end, 5.dp)
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
        IconButton(
            modifier = Modifier.layoutId("logOut"),
            onClick = { onNavigate(ProfileScreen.AUTHORIZATION) }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "Exit",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.profile_im),
            contentDescription = "Image",
            modifier = Modifier
                .size(278.dp)
                .layoutId("image"),
        )
    }
}
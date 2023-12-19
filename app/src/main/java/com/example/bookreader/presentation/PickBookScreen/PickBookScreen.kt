package com.example.bookreader.presentation.PickBookScreen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bookreader.R
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.utils.Root
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import kotlinx.coroutines.delay

@Composable
fun PickBookScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            TopBook(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.08f)

            )
            MainBook(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}


@Composable
fun MainBook(
    modifier: Modifier = Modifier
) {
    val bookUrl = "https://www.100bestbooks.ru/files/Bulgakov_Master_i_Margarita.pdf"
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Remote(bookUrl),
        isZoomEnable = true
    )
    var isLoading by remember { mutableStateOf(true) }

    AnimatedVisibility(visible = isLoading) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("load_book.json"))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BlueLight)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
        }

    }
    LaunchedEffect(key1 = true){
        delay(7000)
        isLoading = false
    }
    VerticalPDFReader(
        state = pdfState,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    )
}
@Composable
fun TopBook(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val constrains = ConstraintSet {
        val text = createRefFor("text")
        val backArrow = createRefFor("backArrow")

        constrain(backArrow) {
            top.linkTo(parent.top, 5.dp)
            start.linkTo(parent.start, 5.dp)
        }
        constrain(text) {
            start.linkTo(backArrow.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 8.dp)
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
                bottomEnd = 0.dp,
                bottomStart = 0.dp,
            ),
            colors = CardDefaults.cardColors(BlueLight),
            content = {}
        )
        IconButton(
            modifier = Modifier.layoutId("backArrow"),
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Text(
            text = "Книга",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("text")
        )
    }
}
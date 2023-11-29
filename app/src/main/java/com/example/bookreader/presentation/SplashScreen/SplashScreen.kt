package com.example.bookreader.presentation.SplashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.utils.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("anim.json"))
    LaunchedEffect(key1 = true) {
        delay(4000)
        navController.navigate(Routes.PROFILE)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        LottieAnimation(
            composition = composition
        )
    }
}
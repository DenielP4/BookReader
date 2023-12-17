package com.example.bookreader.presentation.SplashScreen

import android.util.Log
import android.window.SplashScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.utils.Root
import com.example.bookreader.presentation.utils.Routes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SplashScreen(
    navController: NavController
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset("anim.json"))
    var isPlaying by remember {
        mutableStateOf(false)
    }
    var isProgress by remember {
        mutableStateOf(false)
    }
    val max = 0.25f
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1,
        clipSpec = LottieClipSpec.Progress(0f,max)
    )
    LaunchedEffect(key1 = progress) {
        Log.d("Progress ", "$progress")
        if (progress == 0f) {
            isPlaying = true
        }

        if (progress == max) {
            isPlaying = false
            isProgress = true
        }
        if (isProgress){
            navController.navigate(Root.MAIN_SCREEN)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress
        )
    }
}
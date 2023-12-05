package com.example.bookreader.presentation.MainScreen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookreader.presentation.navigation.AppNavigationGraph
import com.example.bookreader.presentation.navigation.BottomNav

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) {
        AppNavigationGraph(navController = navController)
    }
}
package com.example.bookreader.presentation.navigation

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.BlueLight

@Composable
fun BottomNav(
    navController: NavController
) {
    val listItem = listOf(
        BottomNavItem.Search,
        BottomNavItem.UserBook,
        BottomNavItem.Profile
    )
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = listItem.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            containerColor = BlueLight
        ) {
            listItem.forEachIndexed { index, bottomNavItem ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        navController.navigate(bottomNavItem.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = BlueLight
                    ),
                    label = {
                        Text(
                            text = bottomNavItem.title,
                            color = if (index == selectedIndex) Color.White else BlueDark
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = bottomNavItem.iconId),
                            contentDescription = bottomNavItem.title,
                            tint = if (index == selectedIndex) Color.White else BlueDark
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        }
    }
}
package com.example.bookreader.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.BlueLight
import kotlin.math.roundToInt

@Composable
fun BottomNav(
    navController: NavController,
    bottomBarHeight: Dp,
    bottomBarOffsetHeightPx: MutableFloatState,
    onTwo: (MutableFloatState) -> Unit
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
    onTwo(bottomBarOffsetHeightPx)
    if (bottomBarDestination) {
        BottomAppBar(
            modifier = androidx.compose.ui.Modifier
                .height(bottomBarHeight)
                .offset {
                    IntOffset(x = 0, y = -bottomBarOffsetHeightPx.floatValue.roundToInt())
                },
            backgroundColor = BlueLight
        ) {
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
}

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
import com.example.bookreader.presentation.ui.theme.BlueDark
import com.example.bookreader.presentation.ui.theme.BlueLight

@Composable
fun BottomNav(
    onNavigate: (String) -> Unit
    ) {
    val listItem = listOf(
        BottomNavItem.Search,
        BottomNavItem.UserBook,
        BottomNavItem.Profile
    )
    var selectedIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar(
        containerColor = BlueLight
    ) {
        listItem.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    onNavigate(bottomNavItem.route)
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
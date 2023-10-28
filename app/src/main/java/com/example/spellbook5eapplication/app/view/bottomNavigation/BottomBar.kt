package com.example.spellbook5eapplication.app.view.bottomNavigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState

@Composable
fun BottomBar(
    navController: NavHostController,
    globalOverlayState: GlobalOverlayState,
){
    val bottomNavItems = listOf(
        Screens.Favorite,
        Screens.Search,
        Screens.Spellbooks,
        Screens.Homebrew,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.primary_dark)
    )
    {
        bottomNavItems.forEach { screen ->
            AddItem(
                bottomNavItem = screen,
                currentDestination = currentDestination,
                navController = navController,
                globalOverlayState = globalOverlayState
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    bottomNavItem : Screens,
    currentDestination : NavDestination?,
    navController: NavHostController,
    globalOverlayState: GlobalOverlayState)
{
    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true,
        onClick = {
            if (globalOverlayState.getOverlayStack().isNotEmpty()) {
                globalOverlayState.dismissOverlay()
            }
            navController.navigate(bottomNavItem.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        icon = {
            Icon(
                imageVector = bottomNavItem.icon,
                contentDescription = "Navigation icon")
        },
        selectedContentColor = colorResource(id = R.color.white),
        unselectedContentColor = colorResource(id = R.color.secondary_dark)
    )
}
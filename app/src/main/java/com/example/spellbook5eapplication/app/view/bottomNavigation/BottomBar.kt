package com.example.spellbook5eapplication.app.view.bottomNavigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState

@Composable
fun BottomBar(
    navController: NavHostController,
){
    val bottomNavItems = listOf(
        Screens.Search,
        Screens.Spellbooks,
        Screens.Favorite,
        Screens.QuickPlay,
        Screens.Homebrew,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary
    )
    {
        bottomNavItems.forEach { screen ->
            AddItem(
                bottomNavItem = screen,
                currentDestination = currentDestination,
                navController = navController,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    bottomNavItem : Screens,
    currentDestination : NavDestination?,
    navController: NavHostController,
    )
{
    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true,
        onClick = {
            if (GlobalOverlayState.getOverlayStack().isNotEmpty()) {
                GlobalOverlayState.dismissOverlay()
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
                contentDescription = "Navigation icon",
                modifier = Modifier.size(35.dp))
        },
        selectedContentColor = MaterialTheme.colorScheme.onPrimary,
        unselectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )
}
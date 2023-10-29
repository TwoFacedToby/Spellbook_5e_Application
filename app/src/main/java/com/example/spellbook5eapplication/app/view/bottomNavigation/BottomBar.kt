package com.example.spellbook5eapplication.app.view.bottomNavigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.R

@Composable
fun BottomBar(
    navController: NavHostController
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
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    bottomNavItem : Screens,
    currentDestination : NavDestination?,
    navController: NavHostController)
{
    BottomNavigationItem(
        selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true,
        onClick = {
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

@Preview
@Composable
fun BottomBarPreview(){
    BottomBar(navController = rememberNavController())
}
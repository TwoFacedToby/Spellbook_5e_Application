package com.example.spellbook5eapplication.app.view.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spellbook5eapplication.app.view.screens.FavoriteScreen
import com.example.spellbook5eapplication.app.view.screens.HomebrewScreen
import com.example.spellbook5eapplication.app.view.screens.SearchScreen
import com.example.spellbook5eapplication.app.view.screens.SpellbooksScreen

@Composable
fun BottomNavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = Screens.Search.route){
        composable(route = Screens.Search.route){
            SearchScreen()
        }
        composable(route = Screens.Favorite.route){
            FavoriteScreen()
        }
        composable(route = Screens.Spellbooks.route){
            SpellbooksScreen()
        }
        composable(route = Screens.Homebrew.route){
            HomebrewScreen()
        }
    }
}
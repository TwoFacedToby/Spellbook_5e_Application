package com.example.spellbook5eapplication.app.view.bottomNavigation

import FavoriteScreen
import SpellQueryViewModel
import SpellbooksScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spellbook5eapplication.app.view.screens.BrewScreen
import com.example.spellbook5eapplication.app.Model.Favourites
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.view.screens.Basic_Screen
import com.example.spellbook5eapplication.app.view.screens.SearchScreen
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    globalOverlayState: GlobalOverlayState,
    spellController: SpellController,
    spellListLoader: SpelllistLoader,
) {
    NavHost(navController = navController, startDestination = Screens.Search.route){
        composable(route = Screens.Search.route){
            //SearchScreen(globalOverlayState)
            val spellQueryViewModel = viewModel<SpellQueryViewModel>()
            Basic_Screen(globalOverlayState = globalOverlayState, spellQueryViewModel)
        }
        composable(route = Screens.Favorite.route){
            //FavoriteScreen(spellController, spellListLoader, globalOverlayState)
            val spellQueryViewModel = viewModel<SpellQueryViewModel>()
            Basic_Screen(globalOverlayState = globalOverlayState, spellQueryViewModel)
        }
        composable(route = Screens.Spellbooks.route){
            SpellbooksScreen(spellController, spellListLoader, globalOverlayState)
        }
        composable(route = Screens.Homebrew.route){
            BrewScreen(globalOverlayState)
        }
    }
}
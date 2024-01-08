package com.example.spellbook5eapplication.app.view.bottomNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spellbook5eapplication.app.view.screens.Basic_Screen
import com.example.spellbook5eapplication.app.view.utilities.DynamicButtonFactory
import com.example.spellbook5eapplication.app.viewmodel.SpellQueryViewModelFactory

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Screens.Search.route){
        composable(route = Screens.Search.route){
            //SearchScreen(globalOverlayState)
            val spellList = SpellQueryViewModelFactory.create(type = "ALL_SPELLS")
            Basic_Screen(spellList, true)
        }
        composable(route = Screens.Favorite.route){
            //FavoriteScreen(spellController, spellListLoader, globalOverlayState)
            val spellList = SpellQueryViewModelFactory.create(type = "FAVORITES")
            Basic_Screen(spellList, false)
        }
        composable(route = Screens.Spellbooks.route){
            val spellList = SpellQueryViewModelFactory.create(type = "SPELLBOOK")
            Basic_Screen(spellsLiveData = spellList, false, customContent = {
                DynamicButtonFactory(
                    buttonType = "SPELLBOOK",
                )
            })
        }
        composable(route = Screens.Homebrew.route){
            val spellList = SpellQueryViewModelFactory.create(type = "HOMEBREW")
            Basic_Screen(spellsLiveData = spellList, false, customContent = {
                DynamicButtonFactory(
                    buttonType = "HOMEBREW",
                )
            })
        }
    }
}
package com.example.spellbook5eapplication.app.view

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.view.bottomNavigation.BottomBar
import com.example.spellbook5eapplication.app.view.bottomNavigation.BottomNavigationGraph
import com.example.spellbook5eapplication.app.view.topNavigation.TopBar
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(spellController: SpellController, spellListLoader: SpelllistLoader){
    val navController = rememberNavController()
    val globalOverlayState = GlobalOverlayState()
    Scaffold(
        topBar = { TopBar(navController = navController, globalOverlayState)},
        bottomBar = { BottomBar(navController = navController, globalOverlayState) }
    ){
        BottomNavigationGraph(
            navController = navController,
            globalOverlayState = globalOverlayState,
            spellController = spellController,
            spellListLoader = spellListLoader
        )
    }
}
package com.example.spellbook5eapplication.app.view

import SignInViewModel
import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.app.Repository.SpellController
import com.example.spellbook5eapplication.app.Repository.SpelllistLoader
import com.example.spellbook5eapplication.app.view.bottomNavigation.BottomBar
import com.example.spellbook5eapplication.app.view.bottomNavigation.BottomNavigationGraph
import com.example.spellbook5eapplication.app.view.topNavigation.TopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(signInViewModel: SignInViewModel){
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar(navController = navController, signInViewModel = signInViewModel)},
        bottomBar = { BottomBar(navController = navController) }
    ){
        BottomNavigationGraph(
            navController = navController,
        )
    }
}
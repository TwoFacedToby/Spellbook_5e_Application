package com.example.spellbook5eapplication.app.view.bottomNavigation

import CreateAccountScreen
import GoogleAuthUIClient
import SignInViewModel
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.spellbook5eapplication.app.view.AuthUI.LoginScreen
import com.example.spellbook5eapplication.app.view.screens.Basic_Screen
import com.example.spellbook5eapplication.app.view.screens.QuickPlay
import com.example.spellbook5eapplication.app.view.viewutilities.DynamicButtonFactory
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.SpellQueryViewModelFactory
import com.example.spellbook5eapplication.app.viewmodel.TitleState

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
) {


    NavHost(navController = navController, startDestination = Screens.Search.route){
        composable(route = Screens.Search.route){
            val spellList = SpellQueryViewModelFactory.create(type = "ALL_SPELLS")
            Basic_Screen(spellList, true, true, true)
        }
        composable(route = Screens.Favorite.route){
            val spellList = SpellQueryViewModelFactory.create(type = "FAVORITES")
            Basic_Screen(spellList, false, false, false)
        }
        composable(route = Screens.Spellbooks.route){
            val buttonType = if (TitleState.currentTitle.value != null) "VIEW_SPELLS" else "SPELLBOOK"

            val spellList = SpellQueryViewModelFactory.create(type = "SPELLBOOK")


            Basic_Screen(spellsLiveData = spellList, false, false, true, customContent = {
                Spacer(modifier = Modifier.height(20.dp))
                DynamicButtonFactory(
                    buttonType = buttonType,
                    navController = navController
                )
            })
        }
        composable(route = Screens.Homebrew.route){
            val spellList = SpellQueryViewModelFactory.create(type = "HOMEBREW")
            Basic_Screen(spellsLiveData = spellList, false, false, true, customContent = {
                Spacer(modifier = Modifier.height(20.dp))
                DynamicButtonFactory(
                    buttonType = "HOMEBREW",
                    navController = navController
                )
            })
        }
        composable(route = Screens.QuickPlay.route){
            QuickPlay()
        }
        composable(route = Screens.Login.route){
            val Context = LocalContext.current
            val googleAuthUIClient = GoogleAuthUIClient(context = Context)
            val signInViewModel = SignInViewModel(googleAuthUIClient = googleAuthUIClient)
            LoginScreen(signInViewModel = signInViewModel, onDismissRequest = {GlobalOverlayState.dismissOverlay()}, navController = navController)
        }
        composable(route = Screens.CreateAccount.route){
            val Context = LocalContext.current
            val googleAuthUIClient = GoogleAuthUIClient(context = Context)
            val signInViewModel = SignInViewModel(googleAuthUIClient = googleAuthUIClient)
            CreateAccountScreen(signInViewModel = signInViewModel, onDismissRequest = {GlobalOverlayState.dismissOverlay()}, navController = navController)
        }
    }
}
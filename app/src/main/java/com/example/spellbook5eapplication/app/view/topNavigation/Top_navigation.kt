package com.example.spellbook5eapplication.app.view.topNavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.Overlays.SettingsOverlay
import com.example.spellbook5eapplication.app.view.Overlays.UserOverlay
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, globalOverlayState: GlobalOverlayState){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentTitle = Screens.titleForRoute(currentRoute)

    Box(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = currentTitle,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { globalOverlayState.showOverlay(OverlayType.SETTINGS) }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings button",
                        tint = colorResource(id = R.color.white)
                    )
                }
            },
            actions = {
                IconButton(onClick = { globalOverlayState.showOverlay(OverlayType.PROFILE) }) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile button",
                        tint = colorResource(id = R.color.white)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(id = R.color.primary_dark),
                titleContentColor = colorResource(id = R.color.white)
            )
        )
    }
    when (globalOverlayState.getTopOverlay()) {
        OverlayType.SETTINGS -> {
            CustomOverlay(globalOverlayState, OverlayType.SETTINGS, onDismissRequest = { globalOverlayState.dismissOverlay() }) {
                SettingsOverlay(onDismissRequest = { globalOverlayState.dismissOverlay() })
            }
        }
        OverlayType.PROFILE -> {
            CustomOverlay(globalOverlayState, OverlayType.PROFILE, onDismissRequest = { globalOverlayState.dismissOverlay() }) {
                UserOverlay(onDismissRequest = { globalOverlayState.dismissOverlay() })
            }
        }
        else -> Unit // Do nothing
    }
}
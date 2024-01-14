package com.example.spellbook5eapplication.app.view.topNavigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.Overlays.SettingsOverlay
import com.example.spellbook5eapplication.app.view.Overlays.UserOverlay
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.view.viewutilities.CustomOverlay
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.MainViewModel
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.TitleState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navigationTitle = Screens.titleForRoute(currentRoute)

    val customTitle = TitleState.currentTitle.value

    val currentTitle = customTitle ?: navigationTitle



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
                IconButton(onClick = {
                    GlobalOverlayState.dismissAllOverlays()
                    GlobalOverlayState.showOverlay(OverlayType.SETTINGS) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings button",
                        tint = if (GlobalOverlayState.isOverlayVisible(OverlayType.SETTINGS)) {
                            colorResource(id = R.color.white)
                        } else {
                            colorResource(id = R.color.unselected_icon)
                        },
                        modifier = Modifier.size(35.dp)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        GlobalOverlayState.dismissAllOverlays()
                        GlobalOverlayState.showOverlay(OverlayType.PROFILE)
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Profile button",
                        tint = if (GlobalOverlayState.isOverlayVisible(OverlayType.PROFILE)) {
                            colorResource(id = R.color.white)
                        } else {
                            colorResource(id = R.color.unselected_icon)
                        },
                        modifier = Modifier.size(35.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorResource(id = R.color.main_color),
                titleContentColor = colorResource(id = R.color.white)
            )
        )
    }
    when (GlobalOverlayState.getTopOverlay()) {
        OverlayType.SETTINGS -> {
            CustomOverlay(OverlayType.SETTINGS) {
                SettingsOverlay(onDismissRequest = { GlobalOverlayState.dismissOverlay() })
            }
        }
        OverlayType.PROFILE -> {
            CustomOverlay(OverlayType.PROFILE) {
                UserOverlay(onDismissRequest = { GlobalOverlayState.dismissOverlay() })
            }
        }
        else -> Unit // Do nothing
    }
}
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.Overlays.SettingsOverlay
import com.example.spellbook5eapplication.app.view.Overlays.UserOverlay
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentTitle = Screens.titleForRoute(currentRoute)

    var isSettingsOverlayVisible by remember { mutableStateOf(false) }
    var isUserOverlayVisible by remember { mutableStateOf(false) }
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
                IconButton(onClick = { isSettingsOverlayVisible = true }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings button",
                        tint = colorResource(id = R.color.white)
                    )
                }
            },
            actions = {
                IconButton(onClick = { isUserOverlayVisible = true }) {
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
    if (isSettingsOverlayVisible) {
        CustomOverlay(onDismissRequest = {isSettingsOverlayVisible = false}) {
            SettingsOverlay(onDismissRequest = {isSettingsOverlayVisible = false})
        }
    }
    if(isUserOverlayVisible){
        CustomOverlay(onDismissRequest = {isUserOverlayVisible = false}) {
            UserOverlay(onDismissRequest = {isUserOverlayVisible = false})
        }
    }
}
@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    Spellbook5eApplicationTheme {
        TopBar(navController = rememberNavController())
    }
}
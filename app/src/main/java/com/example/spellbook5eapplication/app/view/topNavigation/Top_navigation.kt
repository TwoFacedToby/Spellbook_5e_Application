package com.example.spellbook5eapplication.app.view.topNavigation

import SignInViewModel
import UserOverlay
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.Overlays.SettingsOverlay
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.view.viewutilities.CustomOverlay
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.MainViewModel
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.TitleState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController
, signInViewModel: SignInViewModel){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navigationTitle = Screens.titleForRoute(currentRoute)

    val customTitle = TitleState.currentTitle.value

    val currentTitle = customTitle ?: navigationTitle

    val signInState by signInViewModel.state.collectAsStateWithLifecycle()

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
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
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
                    if (signInState.isSignInSuccessful) {
                        // User is signed in, show the profile image
                        signInState.data?.profilePictureUrl?.let { url ->
                            Image(
                                painter = rememberAsyncImagePainter(model = url),
                                contentDescription = "User Profile",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, colorResource(id = R.color.white), CircleShape)
                            )
                        }
                    } else {
                        // User is not signed in, show default icon
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
            }},
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
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
                UserOverlay(signInViewModel, onDismissRequest = { GlobalOverlayState.dismissOverlay()})
            }
        }
        else -> Unit // Do nothing
    }

    @Composable
    fun UserProfileIcon(userProfilePictureUrl: String?) {
        if (userProfilePictureUrl != null) {
            Image(
                painter = rememberAsyncImagePainter(userProfilePictureUrl),
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colors.onSurface, CircleShape)
            )
        } else {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Default User Profile",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
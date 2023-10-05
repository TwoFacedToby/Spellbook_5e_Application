package com.example.spellbook5eapplication.app.view.topNavigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentTitle = Screens.titleForRoute(currentRoute)

    var isSettingsOverlayVisible by remember { mutableStateOf(false) }
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
                IconButton(onClick = { /*TODO*/ }) {
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
    if(isSettingsOverlayVisible){
        SettingsOverlay { isSettingsOverlayVisible = false }
    }
}
    @Composable
    fun SettingsOverlay(onDismissRequest: () -> Unit) {
        val screenHeight = LocalConfiguration.current.screenHeightDp
        val offsetY = remember { Animatable(screenHeight.toFloat()) }

        val scope = rememberCoroutineScope()

        val onDismiss: () -> Unit = {
            scope.launch {
                offsetY.animateTo(
                    targetValue = screenHeight.toFloat(),
                    animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow))
                onDismissRequest()
            }
        }

        LaunchedEffect(key1 = true) {
            offsetY.animateTo(screenHeight.toFloat() / 3, animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow))  // Only slide up to 1/3 screen height
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.8f))
                .offset(y = offsetY.value.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 2/3).dp)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Settings", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Additional settings UI elements go here...

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = onDismiss) {
                        Text("Dismiss")
                    }
                }
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
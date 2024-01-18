package com.example.spellbook5eapplication.app.view.viewutilities

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.ui.theme.ButtonColors

@Composable
fun DynamicButtonFactory(buttonType: String, navController: NavController) {


    val buttonText = when (buttonType) {
        "HOMEBREW" -> "New Homebrew"
        "SPELLBOOK" -> "New Spellbook"
        "VIEW_SPELLS" -> "Back"
        else -> ""
    }

    val buttonOnClick = {
        when (buttonType) {
            "HOMEBREW" -> {
                Log.d("MINI", "It does come here")
                GlobalOverlayState.showOverlay(
                    OverlayType.MAKE_SPELL,
                )
            }

            "SPELLBOOK" -> {
                GlobalOverlayState.showOverlay(
                    OverlayType.CREATE_SPELLBOOK,
                )
            }

            "VIEW_SPELLS" -> {
                navController.navigate(Screens.Spellbooks.route)
            }
        }
    }

    val buttonColor = if (buttonType == "VIEW_SPELLS") {
        ButtonDefaults.buttonColors(containerColor = Color.Black)
    } else {
        ButtonDefaults.buttonColors(containerColor = ButtonColors.GreenButton)
    }

    if (buttonText.isNotEmpty()) {
        ColouredButton(
            label = buttonText,
            modifier = Modifier.height(48.dp),
            color = buttonColor,
            onClick = { buttonOnClick() }
        )
    }
}
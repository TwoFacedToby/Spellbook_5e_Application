package com.example.spellbook5eapplication.app.view.viewutilities

import android.util.Log
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

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
        ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.black))
    } else {
        ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_button))
    }

    if (buttonText.isNotEmpty()) {
        ColouredButton(
            label = buttonText,
            modifier = Modifier,
            color = buttonColor,
            onClick = { buttonOnClick() }
        )
    }
}
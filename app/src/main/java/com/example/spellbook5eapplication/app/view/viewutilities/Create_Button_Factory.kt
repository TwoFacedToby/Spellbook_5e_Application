package com.example.spellbook5eapplication.app.view.viewutilities

import android.util.Log
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.ui.theme.ButtonColors

@Composable
fun DynamicButtonFactory(buttonType: String) {
    val buttonText = when (buttonType) {
        "HOMEBREW" -> "New Homebrew"
        "SPELLBOOK" -> "New Spellbook"
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
                    OverlayType.ADD_TO_SPELLBOOK,
                )
            }
        }
    }

    if (buttonText.isNotEmpty()) {
        ColouredButton(
            label = buttonText,
            modifier = Modifier,
            color = ButtonDefaults.buttonColors(
                containerColor = ButtonColors.GreenButton
            ),
            onClick = { buttonOnClick() }
        )
    }
}
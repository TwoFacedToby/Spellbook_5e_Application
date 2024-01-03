package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun DynamicButtonFactory(buttonType: String, globalOverlayState: GlobalOverlayState) {
    val buttonText = when (buttonType) {
        "HOMEBREW" -> "New Homebrew"
        "SPELLBOOK" -> "New Spellbook"
        else -> ""
    }

    val buttonOnClick = {
        when (buttonType) {
            "HOMEBREW" -> {
                globalOverlayState.showOverlay(
                    OverlayType.MAKE_SPELL,
                )
            }
            "SPELLBOOK" -> {
                globalOverlayState.showOverlay(
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
                containerColor = colorResource(id = R.color.green_button)
            ),
            onClick = { buttonOnClick() }
        )
    }
}
package com.example.spellbook5eapplication.app.view.Overlays

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellbookViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType


class SpellbookCreator {

    enum class SpellbookPart {
        NAME,
        DC
        // Add more parts as needed
    }

    @Composable
    fun createNewSpellbook(viewModel: CreateSpellbookViewModel) {
        var show by remember { mutableStateOf(SpellbookPart.NAME) }
        var changeShow by remember { mutableStateOf<SpellbookPart?>(null) }
        var alpha by remember { mutableStateOf(1f) }

        val animatedAlpha by animateFloatAsState(
            targetValue = alpha,
            animationSpec = tween(durationMillis = 250)
        )

        LaunchedEffect(animatedAlpha) {
            if (animatedAlpha == 0f) {
                show = changeShow ?: show
                alpha = 1f // Start fade in
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, start = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .height(600.dp)
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.overlay_box_color),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(

                        modifier = Modifier
                            .alpha(animatedAlpha)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(

                            text = displayPartTitle(show),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        when (show) {
                            SpellbookPart.NAME -> Naming(viewModel)

                            // Add cases for other parts as needed
                            else -> {}
                        }

                        Spacer(modifier = Modifier.height(10.dp))


                        // Top navigation buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            if (show != SpellbookPart.NAME) {

                                ColouredButton(
                                    label = "Back",
                                    modifier = Modifier
                                        .height(38.dp)
                                        .width(100.dp),
                                    color = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.selected_button
                                        )
                                    )
                                ) {

                                    changeShow = show.previousSpellbookPart()!!
                                    alpha = 0f
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            ColouredButton(
                                "Cancel", modifier = Modifier
                                    .height(38.dp) // Sets the height of the button
                                    .width(100.dp), color = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(
                                        id = R.color.red_button
                                    )
                                )
                            ) {
                                GlobalOverlayState.showOverlay(
                                    OverlayType.ERASE_PROMPT)
                            }


                            Spacer(modifier = Modifier.width(10.dp))

                            if (show != SpellbookPart.DC) {
                                // Button to move on
                                ColouredButton(
                                    "Next", modifier = Modifier
                                        .height(38.dp) // Sets the height of the button
                                        .width(100.dp), color = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.green_button
                                        )
                                    )
                                ) {
                                    changeShow = show.nextSpellbookPart()!!
                                    alpha = 0f // Start fade out
                                }
                            } else {
                                ColouredButton(
                                    "Finish", modifier = Modifier
                                        .height(38.dp) // Sets the height of the button
                                        .width(100.dp), color = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.green_button
                                        )
                                    )
                                ) {
                                    /*//Save the spell on the device here
                                    createViewModel.saveSpell()
                                    spellQueryViewModel.loadHomebrewList()
                                    GlobalOverlayState.dismissOverlay()*/
                                }
                            }
                        }

                        //NavigationButtons(show, changeShow, alpha)
                    }
                }
            }
        }
    }

    @Composable
    private fun Naming(viewModel: CreateSpellbookViewModel) {
        CreateSpellbookPartDependentRegion(
            description = "Give your spellbook a name",
            userChoice = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    UserInputField(
                        label = if (viewModel.spellbookName.isBlank()) "Spellbook Name" else viewModel.spellbookName,
                        onInputChanged = { viewModel.updateSpellbookName(it) },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                        singleLine = true,
                        imeAction = ImeAction.Default
                    )
                }
            }
        )
    }

    private fun displayPartTitle(part: SpellbookPart): String {
        return when (part) {
            SpellbookPart.NAME -> "Name"
            SpellbookPart.DC -> "DC"
            // Add more cases as needed
        }
    }

    fun SpellbookPart.nextSpellbookPart(): SpellbookPart? = when (this) {
        SpellbookPart.NAME -> SpellbookPart.DC
        SpellbookPart.DC -> null  // No next part after DC
        // Add cases for other parts when they are implemented
    }

    fun SpellbookPart.previousSpellbookPart(): SpellbookPart? = when (this) {
        SpellbookPart.NAME -> null  // No previous part before NAME
        SpellbookPart.DC -> SpellbookPart.NAME
        // Add cases for other parts when they are implemented
    }


    @Composable
    fun CreateSpellbookPartDependentRegion(
        description: String,
        userChoice: @Composable () -> Unit
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = description,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            userChoice()
        }
    }

}
    @Preview
    @Composable
    fun PreviewSpellbookCreator() {
        val viewModel = CreateSpellbookViewModel()
        SpellbookCreator().createNewSpellbook(viewModel)
    }
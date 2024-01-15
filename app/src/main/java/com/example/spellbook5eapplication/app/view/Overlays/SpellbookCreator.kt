package com.example.spellbook5eapplication.app.view.Overlays

import SpellQueryViewModel
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellbookViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType


class SpellbookCreator {

    enum class SpellbookPart {
        NAME,
        DESC,
        IMAGE
    }

    @Composable
    fun createNewSpellbook(viewModel: CreateSpellbookViewModel) {

        val spellQueryViewModel: SpellQueryViewModel = viewModel()

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
                                    .height(38.dp)
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

                            if (show != SpellbookPart.IMAGE) {
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
                                    //Save spellbook
                                    viewModel.saveSpellbook()
                                    spellQueryViewModel.loadSpellBooks()
                                    GlobalOverlayState.dismissOverlay()
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        when (show) {
                            SpellbookPart.NAME -> Naming(viewModel)
                            SpellbookPart.DESC -> Description(viewModel)
                            SpellbookPart.IMAGE -> ImageSelection(viewModel)

                            else -> {}
                        }




                        // Top navigation buttons


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

    @Composable
    private fun Description(viewModel: CreateSpellbookViewModel) {
        CreateSpellbookPartDependentRegion(
            description = "Describe your spellbook\nInclude any special notes, history, or features.",
            userChoice = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    UserInputField(
                        label = if (viewModel.spellbookDescription.isEmpty()) "Spellbook Description" else viewModel.spellbookDescription,
                        onInputChanged = { viewModel.updateSpellbookDescription(it) },
                        modifier = Modifier
                            .size(width = 220.dp, height = 240.dp),
                        singleLine = false,
                        imeAction = ImeAction.Default
                        // Include other properties as needed
                    )
                }
            }
        )
    }


    private fun displayPartTitle(part: SpellbookPart): String {
        return when (part) {
            SpellbookPart.NAME -> "Name"
            SpellbookPart.DESC -> "Description"
            SpellbookPart.IMAGE -> "Image"
        }
    }

    fun SpellbookPart.nextSpellbookPart(): SpellbookPart? = when (this) {
        SpellbookPart.NAME -> SpellbookPart.DESC
        SpellbookPart.DESC -> SpellbookPart.IMAGE
        SpellbookPart.IMAGE -> null
    }

    fun SpellbookPart.previousSpellbookPart(): SpellbookPart? = when (this) {
        SpellbookPart.NAME -> null
        SpellbookPart.DESC -> SpellbookPart.NAME
        SpellbookPart.IMAGE -> SpellbookPart.DESC
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

@Composable
fun ImageSelection(viewModel: CreateSpellbookViewModel) {

    val images = listOf(
        "spellbook_diamond" to R.drawable.spellbook_diamond,
        "spellbook_brown" to R.drawable.spellbook_brown
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select an Image for Your Spellbook",
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(16.dp))

        images.forEach { (identifier, drawable) ->
            val isSelected = identifier == viewModel.selectedImageIdentifier
            ImageCard(
                drawable = drawable,
                isSelected = isSelected,
                onImageSelected = { viewModel.updateSelectedImageIdentifier(identifier) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ImageCard(
    @DrawableRes drawable: Int,
    isSelected: Boolean,
    onImageSelected: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colors.primary else Color.Gray
    val borderWidth = if (isSelected) 2.dp else 1.dp

    Box(
        modifier = Modifier
            .size(100.dp)
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp))
            .clickable { onImageSelected() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = "Spellbook Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}



    @Preview
    @Composable
    fun PreviewSpellbookCreator() {
        val viewModel = CreateSpellbookViewModel()
        SpellbookCreator().createNewSpellbook(viewModel)
    }
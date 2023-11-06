package com.example.spellbook5eapplication.app.view.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.Overlays.AddToSpellBookOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.spellCards.LocalLargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
//import com.example.spellbook5eapplication.app.view.spellCards.SpellCardOverlay
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme


@Composable
fun BrewScreen(globalOverlayState: GlobalOverlayState){

    var showSpellbookOverlay by remember { mutableStateOf(false) }
    var showSpellDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.search_view_background),
                contentDescription = "Search view background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                alpha = 0.5F
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 100.dp, end = 10.dp, bottom = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    // Search field
                    UserInputField(
                        label = "Search",
                        singleLine = true,
                        onInputChanged = {
                                input -> println("User input: $input")
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    // Button to filter
                    FilterButton(
                        onShowFiltersRequest = {
                            globalOverlayState.showOverlay(
                                OverlayType.FILTER,
                            )
                        })
                }
                Spacer(modifier = Modifier.height(5.dp))

                //List of spells
                LazyColumn(
                    modifier = Modifier.height(600.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    item { SpellCard(
                        onFullSpellCardRequest = {
                            globalOverlayState.showOverlay(
                                OverlayType.LARGE_SPELLCARD,
                            )
                        },
                        onAddToSpellbookRequest = {
                            globalOverlayState.showOverlay(
                                OverlayType.ADD_TO_SPELLBOOK,
                            )
                        }
                    ) }
                }
                Spacer(modifier = Modifier.height(5.dp))
                
                //Button to create own spells
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    ColouredButton("New Homebrew", modifier = Modifier, color = ButtonDefaults.buttonColors(containerColor = colorResource(
                        id =R.color.green_button
                    ))){
                        println("Button clicked")
                    }
                }

                // Overlay management
                for (overlayType in globalOverlayState.getOverlayStack()) {
                    when (overlayType) {
                        OverlayType.LARGE_SPELLCARD -> {
                            LocalLargeSpellCardOverlay(globalOverlayState) { globalOverlayState.dismissOverlay() }
                        }
                        OverlayType.ADD_TO_SPELLBOOK -> {
                            CustomOverlay(
                                globalOverlayState = globalOverlayState,
                                overlayType = OverlayType.ADD_TO_SPELLBOOK,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            ) {
                                AddToSpellBookOverlay(
                                    onDismissRequest = { globalOverlayState.dismissOverlay() }
                                )
                            }
                        }
                        OverlayType.FILTER -> {
                            CustomOverlay(
                                globalOverlayState = globalOverlayState,
                                overlayType = OverlayType.FILTER,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            ){
                                FiltersOverlay(onDismissRequest = { globalOverlayState.dismissOverlay() }, onFilterSelected = {/* TODO */})
                            }
                        }
                        else -> Unit
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrewScreenview() {
    Spellbook5eApplicationTheme {
        //val globalOverlay : GlobalOverlayState(darkTheme = false,dynamicColor = false, content = NULL)
        val globalOverlayState = GlobalOverlayState()
        BrewScreen(globalOverlayState = globalOverlayState)
    }
}
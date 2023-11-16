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
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.view.Overlays.AddToSpellBookOverlay
import com.example.spellbook5eapplication.app.view.Overlays.DeleteOverlay
import com.example.spellbook5eapplication.app.view.Overlays.EraseOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.Overlays.NewSpellOverlay
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.spellCards.LocalLargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
//import com.example.spellbook5eapplication.app.view.spellCards.SpellCardOverlay
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme


@Composable
fun BrewScreen(globalOverlayState: GlobalOverlayState) {
    val spellList = SpellController.getAllSpellsList()

    val filter = null
    //val filter = Filter()
    //filter.setSpellName("Fire")
    //filter.addSchool(Filter.School.ABJURATION)
    val nullSpell = Spell_Info.SpellInfo(
        null,
        "Example name",
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
    var overlaySpell by remember { mutableStateOf(nullSpell) }



    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.home_brew_view_background),
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
                    UserInputField(
                        label = "Search",
                        singleLine = true,
                        onInputChanged = { input ->
                            println("User input: $input")
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    FilterButton(
                        onShowFiltersRequest = {
                            globalOverlayState.showOverlay(
                                OverlayType.FILTER,
                            )
                        })
                }

                SpellQuery(
                    filter = filter,
                    spellList = spellList!!,
                    maxSize = false,
                    onFullSpellCardRequest = {
                        overlaySpell = it
                        globalOverlayState.showOverlay(
                            OverlayType.LOCAL_LARGE_SPELLCARD,
                        )
                    },
                    onAddToSpellbookRequest = {
                        overlaySpell = it
                        globalOverlayState.showOverlay(

                            OverlayType.ADD_TO_SPELLBOOK,
                        )
                    }
                )

                //Spacer(modifier = Modifier.height(5.dp))

                //Button to create own spells

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    ColouredButton("New Homebrew",
                        modifier = Modifier,
                        color = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.green_button
                            )
                        ),
                        onClick = {
                            globalOverlayState.showOverlay(
                                OverlayType.MAKE_SPELL,
                            )
                        })
                }

                /*LazyColumn(
                    modifier = Modifier.fillMaxSize(),
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

                        },
                        overlaySpell
                    ) }
                }*/
            }
            for (overlayType in globalOverlayState.getOverlayStack()) {
                when (overlayType) {
                    OverlayType.LOCAL_LARGE_SPELLCARD -> {
                        LocalLargeSpellCardOverlay(
                            globalOverlayState,
                            { globalOverlayState.dismissOverlay() },
                            overlaySpell
                        )
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
                        ) {
                            FiltersOverlay(
                                onDismissRequest = { globalOverlayState.dismissOverlay() },
                                onFilterSelected = {/* TODO */ })
                        }
                    }

                    OverlayType.MAKE_SPELL -> {
                        CustomOverlay(
                            globalOverlayState = globalOverlayState,
                            overlayType = OverlayType.MAKE_SPELL,
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
                        ) {
                            NewSpellOverlay(onDismissRequest = {
                                globalOverlayState.dismissOverlay()

                                //globalOverlayState.showOverlay(
                                //   OverlayType.ERASE_PROMPT
                                // )
                            }, onFilterSelected = {/* TODO */ })
                        }
                    }

                    else -> Unit
                }

            }
        }
    }
}

    // old
    /*
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                //painter = painterResource(id = R.drawable.search_view_background),
                painter = painterResource(id = R.drawable.home_brew_view_background),
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
                        onInputChanged = { input ->
                            println("User input: $input")
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
                    modifier = Modifier.height(500.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        SpellCard(
                            onFullSpellCardRequest = {
                                globalOverlayState.showOverlay(
                                    OverlayType.LOCAL_LARGE_SPELLCARD,
                                )
                            },
                            onAddToSpellbookRequest = {
                                globalOverlayState.showOverlay(
                                    OverlayType.ADD_TO_SPELLBOOK,
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                //Button to create own spells
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    ColouredButton("New Homebrew",
                        modifier = Modifier,
                        color = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.green_button
                            )
                        ),
                        onClick = {
                            globalOverlayState.showOverlay(
                                OverlayType.MAKE_SPELL,
                            )
                        })
                }
            }
                // Overlay management
                for (overlayType in globalOverlayState.getOverlayStack()) {
                    when (overlayType) {

                        OverlayType.LOCAL_LARGE_SPELLCARD -> {
                            LocalLargeSpellCardOverlay(
                                globalOverlayState = globalOverlayState,
                                onDismissRequest = { globalOverlayState.dismissOverlay() },
                                //onDeleteRequest = { globalOverlayState.showOverlay( OverlayType.DELETE_PROMPT ) })

                            )}

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

                        OverlayType.DELETE_PROMPT -> {
                            CustomOverlay(
                                globalOverlayState = globalOverlayState,
                                overlayType = OverlayType.DELETE_PROMPT,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            ){
                            DeleteOverlay(onDismissRequest = { globalOverlayState.dismissOverlay() })
                        }}

                        OverlayType.MAKE_SPELL -> {
                            CustomOverlay(
                                globalOverlayState = globalOverlayState,
                                overlayType = OverlayType.MAKE_SPELL,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            ) {
                                NewSpellOverlay(onDismissRequest = {
                                    globalOverlayState.dismissOverlay()

                                    //globalOverlayState.showOverlay(
                                     //   OverlayType.ERASE_PROMPT
                                   // )
                                }, onFilterSelected = {/* TODO */ })
                            }
                        }

                        /*
                        OverlayType.ERASE_PROMPT -> {
                            CustomOverlay(
                                globalOverlayState = globalOverlayState,
                                overlayType = OverlayType.ERASE_PROMPT,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            ){
                            EraseOverlay(onDismissRequest = { globalOverlayState.dismissOverlay() })
                        }} */
                        else -> Unit
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

     */
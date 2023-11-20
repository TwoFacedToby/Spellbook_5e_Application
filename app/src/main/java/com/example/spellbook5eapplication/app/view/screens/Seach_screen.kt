package com.example.spellbook5eapplication.app.view.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.view.Overlays.AddToSpellBookOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun SearchScreen(globalOverlayState: GlobalOverlayState){
    val wholeSpellList = SpellController.getAllSpellsList();
    var spellList by remember { mutableStateOf(wholeSpellList?.getCopy())}

    val filter = null
    //val filter = Filter()
    //filter.setSpellName("Fire")
    //filter.addSchool(Filter.School.ABJURATION)
    val nullSpell = Spell_Info.SpellInfo(null, "Example name", null , null, null, null , null, null, null , null, null, null , null, null, null , null, null, null , null, null, null, null , null, null, null, null , null, null, null, null , null, null)
    var overlaySpell by remember { mutableStateOf(nullSpell) }



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
                    onFullSpellCardRequest = {
                        overlaySpell = it
                        globalOverlayState.showOverlay(
                            OverlayType.LARGE_SPELLCARD,
                        )
                    },
                    onAddToSpellbookRequest = {
                        overlaySpell = it
                        globalOverlayState.showOverlay(

                            OverlayType.ADD_TO_SPELLBOOK,
                        )
                    }
                )
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
                    OverlayType.LARGE_SPELLCARD -> {
                        LargeSpellCardOverlay(globalOverlayState, { globalOverlayState.dismissOverlay() }, overlaySpell)
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
package com.example.spellbook5eapplication.app.view.screens

import SpellQueryViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.view.Overlays.AddToSpellBookOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.Overlays.NewSpellOverlay
import com.example.spellbook5eapplication.app.view.Overlays.updateFilterWithSearchName
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import kotlinx.coroutines.runBlocking

@Composable
fun Basic_Screen(
                 spellsLiveData: LiveData<List<Displayable?>>,
                 enablePagination: Boolean,
                 customContent: @Composable (() -> Unit)? = null){

    var filter by remember { mutableStateOf(Filter())}


    // Used for testing filter
    println("Current filter: $filter")
    println("Current filter level size: " + filter.getLevel().size)


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
            Column (modifier = Modifier
                .padding(top = 100.dp, bottom = 56.dp)
                .matchParentSize()) {
                // TopBar with Search and Filters
                SearchFilterBar(filter = filter, onFilterChanged = { newFilter ->
                    filter = newFilter
                })
                // List of Spells, taking up all available space
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)){
                    SpellQuery(
                        filter = filter,
                        spellsLiveData = spellsLiveData,
                        enablePagination = enablePagination
                    )
                }

                if (customContent != null) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f))
                    {
                        customContent()
                    }
                }
            }
            OverlayRenderer(overlayStack = GlobalOverlayState.getOverlayStack())
        }

    }
}

@Composable
fun OverlayRenderer(overlayStack: List<OverlayType>) {
    overlayStack.forEach { overlayType ->
        when (overlayType) {
            OverlayType.LARGE_SPELLCARD ->  {
                LargeSpellCardOverlay(GlobalOverlayState, { GlobalOverlayState.dismissOverlay() }, GlobalOverlayState.currentSpell!!)
            }
            else -> {}
        }
    }
}


@Composable
fun SearchFilterBar(filter: Filter,
                    onFilterChanged: (Filter) -> Unit){
    var filter by remember { mutableStateOf(Filter())}

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        UserInputField(
            label = "Search",
            singleLine = true,
            onInputChanged = { input ->
                val updatedFilter = updateFilterWithSearchName(filter, input)
                onFilterChanged(updatedFilter)
            },
            modifier = Modifier.size(height = 24.dp, width =  120.dp),
            imeAction = ImeAction.Search
        )
        Spacer(modifier = Modifier.width(5.dp))
        FilterButton(
            onShowFiltersRequest = {
                /*globalOverlayState.showOverlay(
                    OverlayType.FILTER,
                )*/
            })
    }
}

// Old way of doing Overlay State
/*for (overlayType in GlobalOverlayState.getOverlayStack()) {
                when (overlayType) {
                    OverlayType.LARGE_SPELLCARD -> {
                        LargeSpellCardOverlay(GlobalOverlayState, { GlobalOverlayState.dismissOverlay() }, overlaySpell)
                    }
                    OverlayType.ADD_TO_SPELLBOOK -> {
                        CustomOverlay(
                            globalOverlayState = GlobalOverlayState,
                            overlayType = OverlayType.ADD_TO_SPELLBOOK,
                            onDismissRequest = { GlobalOverlayState.dismissOverlay() }
                        ) {
                            AddToSpellBookOverlay(
                                spellInfo = overlaySpell,
                                onDismissRequest = { GlobalOverlayState.dismissOverlay() }
                            )
                        }
                    }
                    OverlayType.FILTER -> {
                        CustomOverlay(
                            globalOverlayState = GlobalOverlayState,
                            overlayType = OverlayType.FILTER,
                            onDismissRequest = { GlobalOverlayState.dismissOverlay() }
                        ){
                            FiltersOverlay(
                                onDismissRequest = { GlobalOverlayState.dismissOverlay() },
                                currentfilter = filter,
                                createNewFilter = { Filter() },
                                updateFilterState = { newFilter ->
                                    filter = newFilter
                                    println("Filter updated: $filter") }
                            )
                        }
                    }
                    OverlayType.MAKE_SPELL -> {
                        CustomOverlay(
                            globalOverlayState = GlobalOverlayState,
                            overlayType = OverlayType.MAKE_SPELL,
                            onDismissRequest = { GlobalOverlayState.dismissOverlay() }
                        ) {
                            NewSpellOverlay(onDismissRequest = {
                                GlobalOverlayState.dismissOverlay()

                            }, onFilterSelected = {/* TODO */ })
                        }
                    }
                    else -> Unit
                }

            }*/
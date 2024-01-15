package com.example.spellbook5eapplication.app.view.screens

import SpellQueryViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.view.Overlays.CreateOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.Overlays.HomeBrewInstantiator
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.viewutilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.viewutilities.FilterButton
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellViewModel
import com.example.spellbook5eapplication.app.viewmodel.FilterViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun Basic_Screen(
                 spellsLiveData: LiveData<List<Displayable?>>,
                 enablePagination: Boolean,
                 customContent: @Composable (() -> Unit)? = null){

    val filter by remember { mutableStateOf(Filter())}
    Log.d("FilterViewModel", "Basic screen, filter: $filter")
    println("Current filter: $filter")
    println("Current filter level size: " + filter.getLevel().size)

    val bottomPadding = with(LocalDensity.current) { 20.dp.toPx().toInt() }

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
            Column (
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 80.dp, bottom = 50.dp, start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TopBar with Search and Filters
                SearchFilterBar()

                SubcomposeLayout { constraints ->
                    val spellQueryLayout = subcompose("spellQuery") {

                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(3f)
                            .padding(top = 10.dp),
                            contentAlignment = Alignment.Center
                        ){
                            SpellQuery(
                                spellsLiveData = spellsLiveData,
                                enablePagination = enablePagination
                            )
                        }

                    }.first().measure(constraints)

                    val customContentLayout = customContent?.let {
                        subcompose("customContent", customContent).first().measure(constraints)
                    }

                    // Calculate the height for the SpellQuery considering the height of the custom content
                    val spellQueryHeight = if (customContentLayout != null) {
                        constraints.maxHeight - customContentLayout.height - bottomPadding
                    } else {
                        spellQueryLayout.height
                    }

                    layout(constraints.maxWidth, constraints.maxHeight) {
                        // Place the SpellQuery
                        spellQueryLayout.placeRelative(0, 0)

                        // Calculate the horizontal center for the custom content
                        customContentLayout?.let {
                            val xCenter = (constraints.maxWidth - it.width) / 2
                            it.placeRelative(xCenter, spellQueryHeight)
                        }
                    }
                }
                // List of Spells, taking up all available space
                /*Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)){
                    SpellQuery(
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
                }*/
            }
            OverlayRenderer(GlobalOverlayState.getOverlayStack())
        }

    }
}

@Composable
fun OverlayRenderer(overlayStack: List<OverlayType>) {
    overlayStack.forEach { overlayType ->
        when (overlayType) {
            OverlayType.LARGE_SPELLCARD ->  {
                LargeSpellCard(GlobalOverlayState.currentSpell!!)
            }
            OverlayType.FILTER -> {
                CustomOverlay(overlayType = OverlayType.FILTER) {
                    FiltersOverlay()
                }
            }
            OverlayType.MAKE_SPELL -> {
                val createViewModel = CreateSpellViewModel()
                val createView = HomeBrewInstantiator()
                //HomeBrewInstantiator.makeNewSpellFromTheTop(createViewModel)
                createView.makeNewSpellFromTheTop(createViewModel)
            }
            OverlayType.EDIT_SPELL -> {
                val createViewModel = CreateSpellViewModel()
                val createView = HomeBrewInstantiator()
                createViewModel.updateEntireSpell(GlobalOverlayState.currentSpell!!)
                createView.EditSpell(createViewModel)
            }
            OverlayType.ERASE_PROMPT -> {
                CreateOverlay(
                    message = "Exiting now will erase all progress",
                    button1Message = "Cancel",
                    button2Message = "Exit",
                    button2Function = {GlobalOverlayState.dismissOverlay()}
                )
            }
            OverlayType.DELETE_PROMPT -> {
                // To refresh screen and that it is gone
                val spellQueryViewModel: SpellQueryViewModel = viewModel()

                CreateOverlay(
                    message = "Delete ${GlobalOverlayState.currentSpell!!.name}?",
                    button1Message = "Cancel",
                    button2Message = "Delete",
                    button2Function = {
                            LocalDataLoader.deleteFile(GlobalOverlayState.currentSpell!!.index +".json", LocalDataLoader.DataType.HOMEBREW)
                        spellQueryViewModel.loadHomebrewList()
                        GlobalOverlayState.dismissOverlay()}
                )
            }
            else -> {}
        }
    }
}


@Composable
fun SearchFilterBar(
){
    val filterViewModel: FilterViewModel = viewModel()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        UserInputField(
            label = "Search",
            singleLine = true,
            onInputChanged = { input ->
                filterViewModel.updateFilterWithSearchName(input)
            },
            modifier = Modifier.size(height = 48.dp, width = 220.dp),
            imeAction = ImeAction.Search,
            initialInput = ""
        )
        Spacer(modifier = Modifier.width(5.dp))
        FilterButton(
            onShowFiltersRequest = {
                GlobalOverlayState.showOverlay(
                    OverlayType.FILTER,
                )
            }
        )
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
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
                        ) {
                            AddToSpellBookOverlay(
                                spellInfo = overlaySpell,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            )
                        }
                    }
                    OverlayType.FILTER -> {
                        CustomOverlay(
                            globalOverlayState = GlobalOverlayState,
                            overlayType = OverlayType.FILTER,
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
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
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
                        ) {
                            NewSpellOverlay(onDismissRequest = {
                                GlobalOverlayState.dismissOverlay()

                            }, onFilterSelected = {/* TODO */ })
                        }
                    }
                    else -> Unit
                }

            }*/
package com.example.spellbook5eapplication.app.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.Overlays.CreateOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.Overlays.HomeBrewInstantiator
import com.example.spellbook5eapplication.app.view.Overlays.QuickPlaySpellBooks
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.viewutilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.viewutilities.FadeSide
import com.example.spellbook5eapplication.app.view.viewutilities.FilterButton
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.view.viewutilities.fadingEdge
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
                    .padding(top = 60.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SearchFilterBar()

                SubcomposeLayout { constraints ->
                    val spellQueryLayout = subcompose("spellQuery") {

                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(3f)
                            .fadingEdge(
                                side = FadeSide.TOP,
                                color = Color.White.copy(alpha = 0.6F),
                                width = 40.dp,
                                isVisible = true,
                                spec = null
                            )
                            //.padding(top = 10.dp),
                            ,contentAlignment = Alignment.Center
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

                    val spellQueryHeight = if (customContentLayout != null) {
                        constraints.maxHeight - customContentLayout.height - bottomPadding
                    } else {
                        spellQueryLayout.height
                    }

                    layout(constraints.maxWidth, constraints.maxHeight) {
                        spellQueryLayout.placeRelative(0, 0)

                        customContentLayout?.let {
                            val xCenter = (constraints.maxWidth - it.width) / 2
                            it.placeRelative(xCenter, spellQueryHeight)
                        }
                    }
                }
            }
            OverlayRenderer(GlobalOverlayState.getOverlayStack())
        }

    }
}

@Composable
fun OverlayRenderer(overlayStack: List<OverlayType>) {
    overlayStack.forEach { it -> Log.d("Overlay", "active overlays $it") }
    overlayStack.forEach { overlayType ->
        when (overlayType) {
            OverlayType.LARGE_SPELLCARD ->  {
                LargeSpellCard(GlobalOverlayState.currentSpell!!, false)
            }
            OverlayType.LARGE_QUICKSPELLCARD -> {
                LargeSpellCard(spell = GlobalOverlayState.currentSpell!!, fromQuickPlay = true)
            }
            OverlayType.FILTER -> {
                CustomOverlay(overlayType = OverlayType.FILTER) {
                    FiltersOverlay()
                }
            }
            OverlayType.MAKE_SPELL -> {
                val createViewModel = CreateSpellViewModel()
                val createView = HomeBrewInstantiator()
                createView.makeNewSpellFromTheTop(createViewModel)
            }
            OverlayType.ERASE_PROMPT -> {
                CreateOverlay(
                    message = "Exiting now will erase all progress",
                    button1Message = "Cancel",
                    button2Message = "Exit",
                    button2Function = {GlobalOverlayState.dismissOverlay()}
                )
            }
            OverlayType.QUICKPLAY_SPELLBOOK -> {
                CustomOverlay(OverlayType.QUICKPLAY_SPELLBOOK) {
                    QuickPlaySpellBooks()
                }
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
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.6F))
            .padding(20.dp),
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
            imeAction = ImeAction.Search
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
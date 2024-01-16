package com.example.spellbook5eapplication.app.view.screens

import SpellQueryViewModel
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.view.Overlays.CreateOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.Overlays.HomeBrewInstantiator
import com.example.spellbook5eapplication.app.view.Overlays.SpellbookCreator
import com.example.spellbook5eapplication.app.view.Overlays.QuickPlaySpellBooks
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.view.viewutilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.viewutilities.FadeSide
import com.example.spellbook5eapplication.app.view.viewutilities.FilterButton
import com.example.spellbook5eapplication.app.view.viewutilities.OverlayBox
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.view.viewutilities.fadingEdge
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellViewModel
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellbookViewModel
import com.example.spellbook5eapplication.app.viewmodel.FilterViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.TitleState
import androidx.compose.material3.ButtonDefaults as Material3ButtonDefaults

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
            Box(
                modifier = Modifier
                    .fillMaxSize() // Fill the parent size
                    .background(Color.Black.copy(alpha = 0.4f))
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
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4F),
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
            OverlayType.CREATE_SPELLBOOK -> {
                val createSpellbookViewModel = CreateSpellbookViewModel()
                val createView = SpellbookCreator()
                createView.createNewSpellbook(createSpellbookViewModel)
            }
            OverlayType.DELETE_PROMPT -> {
                // To refresh screen and that it is gone
                val spellQueryViewModel: SpellQueryViewModel = viewModel()

                CreateOverlay(
                    message = "Delete ${GlobalOverlayState.currentSpell!!.name}?",
                    button1Message = "Cancel",
                    button2Message = "Delete",
                    button2Function = {
                            LocalDataLoader.deleteFile(GlobalOverlayState.currentSpell!!.index.toString(), LocalDataLoader.DataType.HOMEBREW)
                        spellQueryViewModel.loadHomebrewList()
                        GlobalOverlayState.dismissOverlay()}
                )
            }

            OverlayType.REMOVE_SPELLBOOK -> {
                val spellQueryViewModel : SpellQueryViewModel = viewModel()
                CreateOverlay(
                    message = "Do you want to delete ${GlobalOverlayState.currentSpellbook!!.spellbookName}?",
                    button1Message = "Cancel",
                    button2Message = "Delete",
                    button2Function = {
                        SpellbookManager.removeSpellbook(GlobalOverlayState.currentSpellbook!!.spellbookName)
                        spellQueryViewModel.loadSpellBooks()
                        GlobalOverlayState.dismissOverlay()}
                )

            }
            OverlayType.REMOVE_SPELL_FROM_SPELLBOOK -> {

                val spellQueryViewModel : SpellQueryViewModel = viewModel()
                CreateOverlay(
                    message = "Do you want to remove ${GlobalOverlayState.currentSpell!!.name}",
                    button1Message = "Cancel",
                    button2Message = "Remove",
                    button2Function = {
                        SpellbookManager.removeSpellFromSpellbook(GlobalOverlayState.currentSpellbook!!.spellbookName, GlobalOverlayState.currentSpell!!)
                        spellQueryViewModel.loadSpellsFromSpellbook(GlobalOverlayState.currentSpellbook!!)

                        GlobalOverlayState.dismissOverlay()}
                )

            }
            OverlayType.QUICKPLAY_SPELLBOOK -> {
                CustomOverlay(OverlayType.QUICKPLAY_SPELLBOOK) {
                    QuickPlaySpellBooks()
                }
            }
            OverlayType.ADD_TO_SPELLBOOK -> {
                val spellbooks = SpellbookManager.getAllSpellbooks()
                val spell = GlobalOverlayState.currentSpell!!
                CustomOverlay(OverlayType.ADD_TO_SPELLBOOK) {
                    add_to_spellbook(spellbooks = spellbooks, spell = spell)
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
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4F))
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
/*
@Composable
fun SelectSpellbookDialog(
    spellbooks: List<Spellbook>,
    onDismiss: () -> Unit,
    spell: Spell.SpellInfo
) {
    val something = add_to_spellbook(spellbooks = spellbooks, onDismiss = { /*TODO*/ }, spell = spell)

    CustomOverlay(overlayType = OverlayType.ADD_TO_SPELLBOOK) {
        add_to_spellbook(spellbooks = spellbooks, onDismiss = { /*TODO*/ }, spell = spell)
    }


}*/


@Composable
fun add_to_spellbook(
    spellbooks: List<Spellbook>,
    spell: Spell.SpellInfo
) {
        // Use your custom OverlayBox composable for the dialog content
    Column(
        modifier = Modifier.padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
        Spacer(modifier = Modifier.height(20.dp))
        OverlayBox {
            item {
                Text(
                    text = "Add Spell To Spellbook",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }

            items(spellbooks.size) { index ->
                val spellbook = spellbooks[index]
                if (!spellbook.spells.contains(spell.index)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {

                                spellbook.addSpellToSpellbook(spell.index ?: "")
                                SpellbookManager.saveSpellbookToFile(spellbook.spellbookName)
                                LocalDataLoader
                                    .getContext()
                                    ?.get()
                                    ?.let { context ->
                                        Toast
                                            .makeText(
                                                context,
                                                "${spell.name} added to ${spellbook.spellbookName}",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                Log.d("WEDO", "DISMISS")
                                GlobalOverlayState.dismissOverlay() // close the dialog afterwards
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = spellbook.spellbookName,
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add to spellbook",
                            tint = Color.White,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SpellbookRow(
    spellbook: Spellbook,
    spell: Spell.SpellInfo,
    onDismiss: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Logic to add spell to the spellbook and save
                spellbook.addSpellToSpellbook(spell.index ?: "")
                SpellbookManager.saveSpellbookToFile(spellbook.spellbookName)
                LocalDataLoader
                    .getContext()
                    ?.get()
                    ?.let { context ->
                        Toast
                            .makeText(
                                context,
                                "${spell.name} added to ${spellbook.spellbookName}",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                onDismiss()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = spellbook.spellbookName,
            modifier = Modifier.padding(15.dp),
            color = colorResource(id = R.color.white)
        )
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add to spellbook",
            tint = colorResource(id = R.color.spellcard_button),
            modifier = Modifier.size(35.dp)
        )
    }
}
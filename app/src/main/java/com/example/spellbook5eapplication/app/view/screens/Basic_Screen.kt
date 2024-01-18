package com.example.spellbook5eapplication.app.view.screens

import SpellQueryViewModel
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.Utility.JsonTokenManager
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.view.Overlays.Add_to_spellbook
import com.example.spellbook5eapplication.app.view.Overlays.CreateOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.Overlays.HomeBrewInstantiator
import com.example.spellbook5eapplication.app.view.Overlays.QuickPlaySpellBooks
import com.example.spellbook5eapplication.app.view.Overlays.SpellbookCreator
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.viewutilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.viewutilities.FadeSide
import com.example.spellbook5eapplication.app.view.viewutilities.SearchFilterBar
import com.example.spellbook5eapplication.app.view.viewutilities.fadingEdge
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellViewModel
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellbookViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun Basic_Screen(
                 spellsLiveData: LiveData<List<Displayable?>>,
                 enablePagination: Boolean,
                 seachEnabled: Boolean,
                 customContent: @Composable (() -> Unit)? = null){


    val filter by remember { mutableStateOf(Filter())}
    Log.d("FilterViewModel", "Basic screen, filter: $filter")
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            Column (
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 60.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(3f)
                            .fadingEdge(
                                side = FadeSide.TOP,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4F),
                                width = 40.dp,
                                isVisible = seachEnabled,
                                spec = null
                            )
                            ,contentAlignment = Alignment.TopCenter
                        ){
                            SpellQuery(
                                spellsLiveData = spellsLiveData,
                                enablePagination = enablePagination,
                            )

                            if(seachEnabled) {
                                SearchFilterBar()
                            }

                            if (customContent != null) {
                                Box(modifier = Modifier.padding(20.dp)){
                                    customContent()
                                }
                            }
                        }
                }
            }
            OverlayRenderer(GlobalOverlayState.getOverlayStack())
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
                val spellQueryViewModel: SpellQueryViewModel = viewModel()
                val createSpellViewModel: CreateSpellViewModel = viewModel()

                CreateOverlay(
                    message = "Delete ${GlobalOverlayState.currentSpell!!.name}?",
                    button1Message = "Cancel",
                    button2Message = "Delete",
                    button2Function = {
                            SpellbookManager.removeHomebrewFromSpellbook(GlobalOverlayState.currentSpell!!.index.toString())
                            LocalDataLoader.deleteFile(GlobalOverlayState.currentSpell!!.index.toString(), LocalDataLoader.DataType.HOMEBREW)
                        spellQueryViewModel.loadHomebrewList()
                        createSpellViewModel.deleteSpellFromFirebase(GlobalOverlayState.currentSpell!!.name!!)
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
                        LocalDataLoader
                            .getContext()
                            ?.get()
                            ?.let { context ->
                                Toast
                                    .makeText(
                                        context,
                                        "Removed the spellbook: ${GlobalOverlayState.currentSpellbook!!.spellbookName}",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
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
                        LocalDataLoader
                            .getContext()
                            ?.get()
                            ?.let { context ->
                                Toast
                                    .makeText(
                                        context,
                                        "Removed" + "${GlobalOverlayState.currentSpell!!.name} from ${GlobalOverlayState.currentSpellbook!!.spellbookName}",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }

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
                    Add_to_spellbook(spellbooks = spellbooks, spell = spell)
                }

            }
            OverlayType.SHARE_TOKEN -> {
                val context = LocalContext.current
                val token = JsonTokenManager.tokenMyHomebrew(GlobalOverlayState.currentSpell!!.index!!)
                CreateOverlay(
                    message = "Token: ${token.take(15)}...",
                    button1Message = "Cancel",
                    button2Message = "Copy"
                ) {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Token", token)
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(context, "Token copied to clipboard", Toast.LENGTH_SHORT).show()

                    GlobalOverlayState.dismissOverlay()
                    GlobalOverlayState.showOverlay(
                        OverlayType.LARGE_SPELLCARD,
                    )
                }
            }
            else -> {}
        }
    }
}


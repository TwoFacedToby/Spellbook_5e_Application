package com.example.spellbook5eapplication.app.view.spellCards

import SpellQueryViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.viewutilities.DefaultSpellCardFactory
import com.example.spellbook5eapplication.app.viewmodel.FilterViewModel
import kotlinx.coroutines.launch

const val amountToLoad = 10
const val pagination = true //Run app with pagination
const val bottomDistance = 10 //How many spell cards from the bottom should the next 10 be loaded. (The lower it is, there more loading stops you will see, the higher it is the more spells you will load and might cause the app to be slower sometimes)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SpellQuery(
    spellsLiveData: LiveData<List<Displayable?>>,
    enablePagination: Boolean,
    modifier: Modifier = Modifier
) {

    val spellQueryViewModel: SpellQueryViewModel = viewModel()
    val filterViewModel: FilterViewModel = viewModel()

    val spellCardFactory = DefaultSpellCardFactory()

    val filter by filterViewModel.currentFilter
    Log.d("SpellQuery", "observed filter: $filter")
    val isUpdated by filterViewModel.isUpdated



    //Back-up
    //val spells by spellQueryViewModel.spells.observeAsState(emptyList())

    val spells by spellsLiveData.observeAsState(emptyList())
    val isLoading by spellQueryViewModel.isLoading.observeAsState(false)

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(filter) {
        if(isUpdated){
            spellQueryViewModel.loadSpellsBasedOnFilter(filter)
            filterViewModel.resetUpdatedValue()
        }
    }

    if (spells.isNullOrEmpty() && !isLoading) {
        NoSpellsFoundMessage()
    } else {

        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(spells.size) { index ->
                spells[index]?.let {
                    Log.d("SpellDebug", "Spell at index $index is of type: ${it::class.java}")
                    when (it) {
                        is Spell.SpellInfo -> {
                            val spellCardComposable = spellCardFactory.createSpellCard(it)
                            spellCardComposable()
                        }
                        is Spellbook -> {
                            // New logic for handling Spellbook
                            Log.d("WEMADEIT", "We didnt actually make it")
                            SpellbookCard(
                                spellbook = it
                                )
                        }

                        else -> {}
                    }

                }

                // Handle pagination logic only if enabled
                if (enablePagination && filter.count() == 0) {
                    val shouldLoadMore = index == spells.size - 1 &&
                            !isLoading &&
                            spellQueryViewModel.canLoadMoreSpells()

                    if (shouldLoadMore) {
                        coroutineScope.launch {
                            spellQueryViewModel.loadMoreSpells()
                        }
                    }
                }
            }

            // Loading indicator only when pagination is enabled
            if (isLoading && enablePagination) {
                item { LoadingIndicator() }
            }
        }
    }
}

@Composable
fun NoSpellsFoundMessage() {
    Column(
        modifier = Modifier
            .width(260.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            color = Color.Transparent,
            modifier = Modifier.height(60.dp)
        )
        Text(
            text = "Could not find spells matching filter or no internet connection.",
            color = colorResource(id = R.color.black),
            fontSize = 18.sp
        )
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Loading...",
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}
package com.example.spellbook5eapplication.app.view.spellCards

import SpellQueryViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val amountToLoad = 10
const val pagination = true //Run app with pagination
const val bottomDistance = 10 //How many spell cards from the bottom should the next 10 be loaded. (The lower it is, there more loading stops you will see, the higher it is the more spells you will load and might cause the app to be slower sometimes)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SpellQuery(
    filter: Filter,
    spellsLiveData: LiveData<List<Spell_Info.SpellInfo?>>,
    onFullSpellCardRequest: (Spell_Info.SpellInfo) -> Unit,
    onAddToSpellbookRequest: (Spell_Info.SpellInfo) -> Unit,
    enablePagination: Boolean,
    modifier: Modifier = Modifier
) {

    val spellQueryViewModel: SpellQueryViewModel = viewModel()


    //Back-up
    //val spells by spellQueryViewModel.spells.observeAsState(emptyList())

    val spells by spellsLiveData.observeAsState(emptyList())
    val isLoading by spellQueryViewModel.isLoading.observeAsState(false)

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    if (spells.isEmpty() && !isLoading) {
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
                    SpellCard(
                        onFullSpellCardRequest = { onFullSpellCardRequest(it) },
                        onAddToSpellbookRequest = { onAddToSpellbookRequest(it) },
                        spell = it
                    )
                }

                /*if (index >= spells.size - bottomDistance && !isLoading) {
                    coroutineScope.launch {
                        println("DETTE HER")
                        spellQueryViewModel.loadMoreSpells()
                    }
                }*/

                // Handle pagination logic only if enabled
                if (enablePagination) {
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

            // Loading indicator only when pagination is enabled and isLoading is true
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

package com.example.spellbook5eapplication.app.view.spellCards

import android.annotation.SuppressLint
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
    spellList: SpellList?,
    onFullSpellCardRequest: (Spell_Info.SpellInfo) -> Unit,
    onAddToSpellbookRequest: (Spell_Info.SpellInfo) -> Unit
) {
    if(spellList == null){
        Column(

            modifier= Modifier
                .width(260.dp)
                .fillMaxHeight()
        ){
            Divider(
                color = Color.Transparent,
                modifier = Modifier.height(60.dp))
            Text(
                color = colorResource(id = R.color.black),
                text = "You have no internet connection or downloaded content."
            )
        }
    }
    else{

    var totalSpellsToLoad = spellList.getIndexList().size
    var showing: List<Spell_Info.SpellInfo?>? by remember { mutableStateOf(emptyList()) }


    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope

        LaunchedEffect(filter) {
            // You can check if the data is already loaded, and if not, load it asynchronously.
            if (filter.count() == 0 && spellList.getLoaded() < amountToLoad) {
                // Display a loading indicator or placeholder while loading.
                showing = emptyList() // Show loading indicator or placeholder.

            // Call withContext within the coroutine scope
            coroutineScope.launch {

                    //With Pagination
                    val loadedData = withContext(Dispatchers.IO) {
                        SpellController.loadNextFromSpellList(
                            amountToLoad,
                            spellList
                        ) // Loads data in a background thread.
                    }
                    showing = loadedData // Update the UI with the loaded data.


                }
            } else if (filter.count() > 0 && spellList.getLoaded() != spellList.getIndexList().size) {
                showing = emptyList() // Show loading indicator or placeholder.

                // Call withContext within the coroutine scope
                coroutineScope.launch {
                    val loadedData = withContext(Dispatchers.IO) {
                        SpellController.loadEntireSpellList(spellList) // Loads data in a background thread.
                        SpellController.searchSpellListWithFilter(
                            spellList,
                            filter
                        ) // Filter data in a background thread.
                    }
                    totalSpellsToLoad = loadedData.getSpellInfoList().size
                    showing = loadedData.getSpellInfoList() // Update the UI with the filtered data.

                }
            }
        }

        println("Showing: ${showing?.size}")

        if (showing?.isEmpty() == true) {
            Text(text = "Loading", fontSize = 18.sp, color = Color.White)
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp), // Adjust spacing between items
            ) {
                if (showing != null) {
                    items(showing!!.size) { spellIndex ->
                        showing!![spellIndex]?.let {
                            SpellCard(
                                onFullSpellCardRequest = {
                                    onFullSpellCardRequest(it)
                                },
                                onAddToSpellbookRequest = {
                                    onAddToSpellbookRequest(it)
                                },
                                spell = it
                            )
                        }


                        val distance =
                            if (bottomDistance < showing!!.size) bottomDistance else amountToLoad - 1

                        // Check if the user is close to the end of the list
                        if (spellIndex == showing!!.size - distance && pagination) {
                            // Load more data when the user is close to the end
                            coroutineScope.launch {
                                val loadedData: List<Spell_Info.SpellInfo?>? =
                                    withContext(Dispatchers.IO) {
                                        SpellController.loadNextFromSpellList(amountToLoad, spellList)
                                    }
                                if (loadedData != null) {
                                    for (spell in loadedData) {
                                        showing = showing!! + spell
                                    }
                                }
                            }
                        }
                    }
                    item {

                        Box(
                            modifier = Modifier
                                .padding(bottom = 50.dp)
                                .background(Color.Transparent)
                                .fillMaxWidth()
                            //.align(Alignment.Center)

                        )
                        {
                            if (showing!!.size < totalSpellsToLoad - 1 && filter.count() == 0) {
                                Text(
                                    text = "Loading ${showing!!.size} / ${totalSpellsToLoad - 1}",
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
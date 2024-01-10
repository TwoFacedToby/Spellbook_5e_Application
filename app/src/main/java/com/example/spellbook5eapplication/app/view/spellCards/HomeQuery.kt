package com.example.spellbook5eapplication.app.view.spellCards

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.SpellController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val amountToLoadH = 10
const val paginationH = true //Run app with pagination
const val bottomDistanceH = 10 //How many spell cards from the bottom should the next 10 be loaded. (The lower it is, there more loading stops you will see, the higher it is the more spells you will load and might cause the app to be slower sometimes)
/*@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeQuery(
    filter: Filter?,
    spellList: SpellList,
    maxSize: Boolean,
    onFullSpellCardRequest: (Spell_Info.SpellInfo) -> Unit,
    onAddToSpellbookRequest: (Spell_Info.SpellInfo) -> Unit) {

    var totalSpellsToLoad = spellList.getIndexList().size
    var showing: List<Spell_Info.SpellInfo?>? by remember { mutableStateOf(emptyList()) }


    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope

    showing = spellList.getSpellInfoList()

/*   At first no use of calling for API
    LaunchedEffect(filter) {
        // You can check if the data is already loaded, and if not, load it asynchronously.
        if (filter == null && spellList.getLoaded() < amountToLoad) {
            // Display a loading indicator or placeholder while loading.
            showing = emptyList() // Show loading indicator or placeholder.

            // Call withContext within the coroutine scope
            coroutineScope.launch {

                //With Pagination
                val loadedData = withContext(Dispatchers.IO) {
                    SpellController.loadNextFromSpellList(amountToLoad, spellList) // Loads data in a background thread.
                }
                showing = loadedData // Update the UI with the loaded data.



            }
        } else if (filter != null && spellList.getLoaded() != spellList.getIndexList().size) {
            showing = emptyList() // Show loading indicator or placeholder.

            // Call withContext within the coroutine scope
            coroutineScope.launch {
                val loadedData = withContext(Dispatchers.IO) {
                    SpellController.loadEntireSpellList(spellList) // Loads data in a background thread.
                    SpellController.searchSpellListWithFilter(spellList, filter) // Filter data in a background thread.
                }
                totalSpellsToLoad = loadedData.getSpellInfoList().size
                showing = loadedData.getSpellInfoList() // Update the UI with the filtered data.

            }
        }
    }
    */

    println("Showing: ${showing?.size}")

    var modifierUsed: Modifier

if(maxSize){
    modifierUsed = Modifier
        .fillMaxWidth()
        .fillMaxHeight()}
    else{
        modifierUsed = Modifier
            .fillMaxWidth()
            .height(450.dp)}

    Box(
        modifier = modifierUsed,
        contentAlignment = Alignment.TopCenter
    ) {
        if (showing?.isEmpty() == true) {
            Text(text = "Loading", fontSize = 18.sp, color = Color.White)
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    //Playing here
                    //.height(500.dp)
                    .padding(horizontal = 10.dp)
                    .align(Alignment.TopCenter),
                contentPadding = PaddingValues(bottom = 16.dp), // Add padding to the content
                verticalArrangement = Arrangement.spacedBy(8.dp), // Adjust spacing between items
            ) {
                if(showing != null){
                    items(showing!!.size) { spellIndex ->
                        showing!![spellIndex]?.let {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp)){//Centering is weird idk what to tell you
                                /*SpellCard(
                                    onFullSpellCardRequest = {
                                        onFullSpellCardRequest(it)
                                    },
                                    onAddToSpellbookRequest = {
                                        onAddToSpellbookRequest(it)
                                    },
                                    spell = it
                                )*/
                            }

                        }




/*
                        val distance = if(bottomDistanceH < showing!!.size) bottomDistanceH else amountToLoadH-1

                        // Check if the user is close to the end of the list
                        if (spellIndex == showing!!.size - distance && paginationH) {
                            // Load more data when the user is close to the end
                            coroutineScope.launch {
                                val loadedData : List<Spell_Info.SpellInfo?>? = withContext(Dispatchers.IO) {
                                    SpellController.loadNextFromSpellList(amountToLoadH, spellList)
                                }
                                if (loadedData != null) {
                                    for(spell in loadedData){
                                        showing = showing!! + spell
                                    }
                                }
                            }
                        }
                        */
                    }
                    item {

                            Box(
                                modifier = Modifier
                                    .padding(bottom = 50.dp)
                                    .background(Color.Transparent)
                                    .fillMaxWidth()
                                    .align(Alignment.Center)

                            )
                            {
                                if(showing!!.size < totalSpellsToLoad - 1 && filter == null) {
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
}*/
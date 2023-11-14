package com.example.spellbook5eapplication.app.view.spellCards

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SpellQuery(filter: Filter?, spellList: SpellList, onDialogRequest: () -> Unit, onOverlayRequest: () -> Unit) {
    var showing: List<Spell_Info.SpellInfo?>? by remember { mutableStateOf(emptyList()) }
    var pagination = false

    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope

    LaunchedEffect(filter) {
        // You can check if the data is already loaded, and if not, load it asynchronously.
        if (filter == null && spellList.getLoaded() < 5) {
            // Display a loading indicator or placeholder while loading.
            showing = emptyList() // Show loading indicator or placeholder.

            // Call withContext within the coroutine scope
            coroutineScope.launch {
                val loadedData = withContext(Dispatchers.IO) {
                    SpellController.loadNextFromSpellList(10, spellList) // Loads data in a background thread.
                }
                showing = loadedData // Update the UI with the loaded data.
                pagination = true
            }
        } else if (filter != null && spellList.getLoaded() != spellList.getIndexList().size) {
            showing = emptyList() // Show loading indicator or placeholder.

            // Call withContext within the coroutine scope
            coroutineScope.launch {
                val loadedData = withContext(Dispatchers.IO) {
                    SpellController.loadEntireSpellList(spellList) // Loads data in a background thread.
                    SpellController.searchSpellListWithFilter(spellList, filter) // Filter data in a background thread.
                }
                showing = loadedData.getSpellInfoList() // Update the UI with the filtered data.
            }
        }
    }

    println("Showing: ${showing?.size}")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        if (showing?.isEmpty() == true) {
            Text(text = "Loading", fontSize = 18.sp, color = Color.White)
        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .padding(horizontal = 10.dp), // Add padding to the LazyColumn
                contentPadding = PaddingValues(bottom = 16.dp), // Add padding to the content
                verticalArrangement = Arrangement.spacedBy(8.dp), // Adjust spacing between items
            ) {
                if(showing != null){
                    items(showing!!.size) { spellIndex ->
                        showing!![spellIndex]?.let {
                            SpellCard(
                                onDialogRequest = { onDialogRequest },
                                onOverlayRequest = { onOverlayRequest },
                                spell = it
                            )
                        }

                        // Check if the user is close to the end of the list
                        if (spellIndex == showing!!.size - 1 && !pagination) {
                            // Load more data when the user is close to the end
                            pagination = true
                            // Call withContext within the coroutine scope
                            coroutineScope.launch {
                                // Load more data and append it to the existing list
                                val loadedData : List<Spell_Info.SpellInfo?>? = withContext(Dispatchers.IO) {
                                    SpellController.loadNextFromSpellList(10, spellList)
                                }
                                if (loadedData != null) {
                                    for(spell in loadedData){
                                        showing = showing!! + spell
                                    }
                                }
                                pagination = false
                            }
                        }
                    }
                }

            }
        }
    }
}
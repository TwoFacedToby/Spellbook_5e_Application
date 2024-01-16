package com.example.spellbook5eapplication.app.view.spellCards

import SpellQueryViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.ui.theme.ButtonColors
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LargeSpellCard(spell: Spell.SpellInfo, fromQuickPlay: Boolean) {

    val images = SpellCardCreation(spell)
    val spellbooks = SpellbookManager.getAllSpellbooks()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { GlobalOverlayState.dismissOverlay() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(10.dp)
                .clickable { /*Nothing here, it consumes the outer box's click event*/ }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1F)
                        ) {
                            Text(
                                text = spell.name ?: "UNIDENTIFIED",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                modifier = Modifier.padding(5.dp, 0.dp),
                                color = Color.Black
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(1F),
                            horizontalArrangement = if (!fromQuickPlay) Arrangement.SpaceEvenly else Arrangement.End
                        ) {
                            if (!fromQuickPlay) {
                                IconButton(
                                    onClick = {
                                        GlobalOverlayState.currentSpell = spell
                                        GlobalOverlayState.showOverlay(OverlayType.ADD_TO_SPELLBOOK)
                                    }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Add,
                                        contentDescription = "Add to Spellbook",
                                        tint = ButtonColors.SpellCardButton,
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                                val defaultFavouriteImage = Icons.Outlined.FavoriteBorder
                                var favouriteImage by remember {
                                    mutableStateOf(
                                        defaultFavouriteImage
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        spell.index?.let { spellIndex ->
                                            val favouritesSpellbook =
                                                SpellbookManager.getSpellbook("Favourites")
                                            favouriteImage =
                                                if (favouritesSpellbook?.spells?.contains(spellIndex) == true) {
                                                    // Remove spell from favorites
                                                    favouritesSpellbook.removeSpell(spellIndex)
                                                    defaultFavouriteImage
                                                } else {
                                                    // Add spell to favorites
                                                    favouritesSpellbook?.addSpellToSpellbook(
                                                        spellIndex
                                                    )
                                                    Icons.Filled.Favorite // Change this to the filled heart icon
                                                }
                                            // Save the updated favorites list
                                            CoroutineScope(Dispatchers.IO).launch {
                                                SpellbookManager.saveSpellbookToFile("Favourites")
                                                println("Favorites updated")
                                            }
                                        }
                                    }
                                ) {
                                    if (SpellbookManager.getSpellbook("Favourites")?.spells?.contains(
                                            spell.index
                                        ) == true
                                    ) {
                                        favouriteImage = Icons.Filled.Favorite
                                    }
                                    Icon(
                                        imageVector = favouriteImage,
                                        contentDescription = "Favorite button",
                                        tint = ButtonColors.SpellCardButton,
                                        modifier = Modifier.size(48.dp)
                                    )
                                }
                            }
                            IconButton(
                                onClick = { GlobalOverlayState.dismissOverlay() }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Close",
                                    tint = ButtonColors.SpellCardButton,
                                    modifier = Modifier.size(48.dp)
                                )

                            }
                        }
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 2.dp,
                        modifier = Modifier.padding(0.dp, 5.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        val lazyListState = rememberLazyListState()
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            state = lazyListState

                        ) {
                            items(images.classImageIDs.size) { index ->
                                Image(
                                    painter = painterResource(id = images.classImageIDs[index]),
                                    contentDescription = "Class",
                                    modifier = Modifier
                                        .size(21.dp)
                                        .padding(1.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .border(
                                            0.5.dp,
                                            MaterialTheme.colorScheme.tertiary,
                                            shape = RoundedCornerShape(2.dp)
                                        )
                                        .shadow(elevation = 5.dp)
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(2F)
                            .padding(0.dp, 5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(4F)
                        ) {
                            SpellInfoNew(spell)
                        }
                        Box(
                            modifier = Modifier
                                .weight(1F),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Image(
                                painter = painterResource(id = images.schoolID),
                                contentDescription = "Spell school",
                                modifier = Modifier
                                    .size(60.dp, 60.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .border(
                                        0.5.dp,
                                        MaterialTheme.colorScheme.tertiary,
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(7F),
                    ) {
                        Text(
                            text = "Spell description",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxHeight()
                        ) {
                            val combinedDescriptions =
                                (spell.desc ?: emptyList()) + (spell.atHigherLevel
                                    ?: emptyList())
                            items(combinedDescriptions.size) { index ->
                                if (spell.atHigherLevel?.isNotEmpty() == true)
                                    if (combinedDescriptions[index] == spell.atHigherLevel?.get(
                                            0
                                        )
                                    ) {
                                        Text(
                                            text = "At Higher Levels",
                                            color = Color.Black,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                Text(
                                    text = combinedDescriptions[index],
                                    color = colorResource(id = R.color.black),
                                    fontSize = 12.sp
                                )
                                Divider(
                                    color = Color.Transparent,
                                    thickness = 40.dp,
                                    modifier = Modifier.padding(5.dp, 0.dp)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (spell.homebrew == true) {


                            IconButton(onClick = {
                                GlobalOverlayState.showOverlay(OverlayType.DELETE_PROMPT)

                            }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = "delete Homebrew",
                                    modifier = Modifier.size(48.dp),
                                    colorResource(id = R.color.spellcard_button)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            IconButton(onClick = {
                                GlobalOverlayState.showOverlay(OverlayType.EDIT_SPELL)
                            }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit Homebrew",
                                    modifier = Modifier.size(48.dp),
                                    ButtonColors.SpellCardButton
                                )
                            }
                        }
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 1.dp,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
                    )
                }
            }
        }
    }

}


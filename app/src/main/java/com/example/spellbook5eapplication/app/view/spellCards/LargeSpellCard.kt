package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LargeSpellCardOverlayOLD(
    spell : Spell.SpellInfo
)
{
        val images = SpellCardCreation(spell)
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.spellcard_color)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .align(Alignment.Center)
                    .padding(10.dp)
            ) {
                Column(modifier = Modifier.weight(4f)) {
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 0.dp, end = 10.dp, start = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        var spellName = "Name Here"
                        if(spell.name != null) spellName = "${spell.name}"
                        Text(
                            text = spellName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(5.dp, 0.dp),
                            color = colorResource(id = R.color.black)
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.End),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { GlobalOverlayState.showOverlay(OverlayType.ADD_TO_SPELLBOOK) }) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = "Add to spellbook",
                                    tint = colorResource(id = R.color.spellcard_button)
                                )
                            }

                            val defaultFavouriteImage = Icons.Outlined.FavoriteBorder
                            var favouriteImage by remember { mutableStateOf(defaultFavouriteImage) }
                            var isFavourite = SpellbookManager.getSpellbook("Favourites")?.spells?.contains(spell.index)
                            IconButton(onClick = {
                                spell.index?.let { spellIndex ->
                                    val favouritesSpellbook = SpellbookManager.getSpellbook("Favourites")
                                    if (favouritesSpellbook?.spells?.contains(spellIndex) == true) {
                                        // Remove spell from favorites
                                        favouritesSpellbook.removeSpell(spellIndex)
                                        favouriteImage = defaultFavouriteImage
                                    } else {
                                        // Add spell to favorites
                                        favouritesSpellbook?.addSpellToSpellbook(spellIndex)
                                        favouriteImage = Icons.Outlined.Favorite // Change this to the filled heart icon
                                    }
                                    // Save the updated favorites list
                                    CoroutineScope(Dispatchers.IO).launch {
                                        SpellbookManager.saveSpellbookToFile("Favourites")
                                        println("Favorites updated")
                                    }
                                }
                            }) {

                                if(SpellbookManager.getSpellbook("Favourites")?.spells?.contains(spell.index) == true)
                                {
                                    favouriteImage = Icons.Outlined.Favorite
                                }
                                Icon(
                                    imageVector = favouriteImage,
                                    contentDescription = "Favorite button",
                                    tint = colorResource(id = R.color.spellcard_button),
                                    modifier = Modifier.size(35.dp)
                                )
                            }

                            IconButton(
                                onClick = { GlobalOverlayState.dismissOverlay() }){
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Close",
                                    tint = colorResource(id = R.color.spellcard_button)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 10.dp, end = 10.dp)
                    )
                    {
                        Divider(
                            color = colorResource(id = R.color.black),
                            thickness = 0.7.dp,
                            modifier = Modifier.padding(5.dp, 0.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .width(220.dp)
                                .padding(start = 10.dp, end = 10.dp)
                        )
                        {
                            val lazyListState = rememberLazyListState()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp),
                                horizontalArrangement = Arrangement.Start,
                                state = lazyListState

                            ) {

                                items(images.classImageIDs.size) { index ->
                                    Image(
                                        painter = painterResource(id = images.classImageIDs[index]),
                                        contentDescription = "Class",
                                        modifier = Modifier
                                            .size(16.dp, 16.dp)
                                            .padding(1.dp)
                                            .clip(RoundedCornerShape(2.dp))
                                            .shadow(elevation = 5.dp)
                                    )
                                }
                            }


                            SpellInfo(spell)
                        }

                        Column(
                            modifier = Modifier.padding(end = 15.dp)
                        ) {
                            Image(
                                painter = painterResource(id = images.schoolID),
                                contentDescription = "Spell school",
                                modifier = Modifier
                                    .size(60.dp, 60.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .border(
                                        0.5.dp,
                                        colorResource(id = R.color.border_color),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .weight(10f)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = "Spell description",
                        modifier = Modifier.padding(start = 10.dp),
                        color = colorResource(id = R.color.black)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxHeight()


                    ){
                        val combinedDescriptions = (spell.desc ?: emptyList()) + (spell.atHigherLevel ?: emptyList())

                        items(combinedDescriptions.size) { index ->
                            if(spell.atHigherLevel?.isNotEmpty() == true)
                                if(combinedDescriptions[index] == spell.atHigherLevel?.get(0)){
                                    Text(
                                        text = "At Higher Levels",
                                        modifier = Modifier
                                            .padding(start = 10.dp),
                                        color = colorResource(id = R.color.black),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            Text(
                                text = combinedDescriptions[index],
                                modifier = Modifier
                                    .padding(start = 10.dp),
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
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom
                )
                {
                    Text(
                        text = "Tags:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp),
                        color = colorResource(id = R.color.black)
                    )
                    Divider(
                        color = colorResource(id = R.color.black),
                        thickness = 1.dp,
                        modifier = Modifier.padding(5.dp, 0.dp)
                    )
                }
            }
        }
    }

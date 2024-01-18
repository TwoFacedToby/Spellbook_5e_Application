package com.example.spellbook5eapplication.app.view.spellCards

import SpellQueryViewModel
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.view.Overlays.CreateOverlay
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.ui.theme.ButtonColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.spellbook5eapplication.app.viewmodel.TitleState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpellCard(
    spell: Spell.SpellInfo
) {
    val cardColor = MaterialTheme.colorScheme.secondary
    val images = SpellCardCreation(spell)
    var showDialog by remember { mutableStateOf(false) }

    val spellbooks = SpellbookManager.getAllSpellbooks()

    val spellQueryViewModel: SpellQueryViewModel = viewModel()

    // Checker to see if we are viewing spells in a spellbook
    val isSpellbookView = TitleState.currentTitle.value != null

    Card(
        elevation = 10.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(10.dp)
            .combinedClickable(
                onClick = {
                    GlobalOverlayState.currentSpell = spell
                    GlobalOverlayState.showOverlay(OverlayType.LARGE_SPELLCARD)
                },
                onLongClick = { if (isSpellbookView)
                    GlobalOverlayState.currentSpell = spell
                    GlobalOverlayState.currentSpellbook = SpellbookManager.getSpellbook(TitleState.currentTitle.value!!)
                    GlobalOverlayState.showOverlay(OverlayType.REMOVE_SPELL_FROM_SPELLBOOK)
                },
            )

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(3F),
                ) {
                    Row{
                        if(spell.index != null){
                            Image(
                                painter = painterResource(id = images.schoolID),
                                contentDescription = "Spell school",
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .border(
                                        0.5.dp,
                                        MaterialTheme.colorScheme.tertiary,
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                        else
                        {
                            Box(
                                modifier = Modifier
                                    .background(colorResource(id = R.color.unselected_icon))
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .border(
                                        0.5.dp,
                                        colorResource(id = R.color.border_color),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 0.dp)
                        ) {
                            Text(
                                text = spell.name ?: "", //loading icon
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                maxLines = 1,
                                color = Color.Black,

                                )
                            Divider(
                                color = Color.Black,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(0.dp, 3.dp)
                            )
                            val lazyListState = rememberLazyListState()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                    }
                    SpellInfoNew(spell)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            GlobalOverlayState.currentSpell = spell
                            GlobalOverlayState.showOverlay(OverlayType.ADD_TO_SPELLBOOK) }) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add to spellbook",
                            tint = ButtonColors.SpellCardButton,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    val defaultFavouriteImage = Icons.Outlined.FavoriteBorder
                    var favouriteImage by remember { mutableStateOf(defaultFavouriteImage) }
                    IconButton(
                        onClick = {
                            spell.index?.let { spellIndex ->
                                val favouritesSpellbook =
                                    SpellbookManager.getSpellbook("favourites")

                                Log.d("LOLOL400", favouritesSpellbook.toString())

                                favouriteImage =
                                    if (favouritesSpellbook?.spells?.contains(spellIndex) == true) {
                                        // Remove spell from favorites
                                        favouritesSpellbook.removeSpell(spellIndex)
                                        SpellbookManager.saveSpellbookToFile(favouritesSpellbook.spellbookName)
                                        defaultFavouriteImage
                                    } else {
                                        // Add spell to favorites
                                        favouritesSpellbook?.addSpellToSpellbook(spellIndex)
                                        SpellbookManager.saveSpellbookToFile(favouritesSpellbook!!.spellbookName)
                                        Icons.Filled.Favorite // Change this to the filled heart icon
                                    }
                            }
                        }
                    ) {
                        if(SpellbookManager.getSpellbook("favourites")?.spells?.contains(spell.index) == true)
                        {
                            favouriteImage = Icons.Outlined.Favorite
                        }
                        Icon(
                            imageVector = favouriteImage,
                            contentDescription = "Favorite button",
                            tint = ButtonColors.SpellCardButton,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun SpellInfoNew(spell: Spell.SpellInfo){
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val fontSize = 14.sp
        val headlineColor = Color.Gray
        val textColor = Color.Black
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F)
        ) {
            Text(
                text = "Level:",
                fontSize = fontSize,
                maxLines = 1,
                color = headlineColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = spell.level.toString(),
                fontSize = fontSize,
                maxLines = 1,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Range:",
                fontSize = fontSize,
                maxLines = 1,
                color = headlineColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = spell.range?: "", //loading icon
                fontSize = fontSize,
                maxLines = 1,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.5F)
        ) {
            Text(
                text = "School:",
                fontSize = fontSize,
                maxLines = 1,
                color = headlineColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = spell.school?.name ?: "", //loading icon
                fontSize = fontSize,
                maxLines = 1,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Duration:",
                fontSize = fontSize,
                maxLines = 1,
                color = headlineColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = spell.duration?: "", //loading icon
                fontSize = fontSize,
                maxLines = 1,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.5F)
        ){
            Text(
                text = "Casting Time:",
                fontSize = fontSize,
                maxLines = 1,
                color = headlineColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = spell.casting_time?: "", //loading icon
                fontSize = fontSize,
                maxLines = 1,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Components:",
                fontSize = fontSize,
                maxLines = 1,
                color = headlineColor,
                overflow = TextOverflow.Ellipsis
            )
            var spellComponents = ""
            if(spell.components != null) {
                for(index in 1..spell.components!!.size){
                    if(index - 1 != 0) spellComponents += ", "
                    spellComponents += spell.components!![index-1]
                }
            }
            Text(
                text = spellComponents,
                fontSize = fontSize,
                maxLines = 1,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun NewSpellCardPreview() {
    SpellCard(
        Spell.SpellInfo(
            null,
            "0",
            "Acid Arrow",
            desc = listOf("A spell"),
            atHigherLevel = listOf("A greater spell"),
            "90 feet",
            components = listOf("V", "S", "M"),
            null,
            false,
            "instantaneous",
            false,
            "instantaneous",
            2,
            school = Spell.SpellSchool("0", "Transmutation", null),
            classes = listOf(Spell.SpellClass("0", "wizard", null)),
            null,
            "range",
            damage = Spell.SpellDamage(damageType = Spell.SpellDamageType("0", "range")),
            "Dex",
            false
        )
    )
}




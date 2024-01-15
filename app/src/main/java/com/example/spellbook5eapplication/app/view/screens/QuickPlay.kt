package com.example.spellbook5eapplication.app.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Class
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.Overlays.FilterButton
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellCardCreation
import com.example.spellbook5eapplication.app.view.spellCards.SpellInfoNew
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.viewmodel.FilterViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.QuickPlayViewModel
import com.example.spellbook5eapplication.ui.theme.ButtonColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun QuickPlay() {
    val backgroundImage = painterResource(id = R.drawable.search_view_background)

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = backgroundImage,
                contentDescription = "Background image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                alpha = 0.5F
            )
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(top = 75.dp, bottom = 65.dp, start = 10.dp, end = 10.dp),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val classes = Class.values()
                    items(classes.size) {
                        ClassCard(type = classes[it])
                    }
                }
            }
        }
        OverlayRenderer(overlayStack = GlobalOverlayState.getOverlayStack())
    }
}

@Composable
fun ClassCard(type: Class) {
    val quickPlayViewModel: QuickPlayViewModel = viewModel()
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .clickable {
                quickPlayViewModel.updateCurrentClass(type)
                GlobalOverlayState.classType = type
                GlobalOverlayState.showOverlay(OverlayType.QUICKPLAY_SPELLBOOK)
            },
        elevation = 20.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colorScheme.secondary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp), contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = Class.className(type), fontSize = 24.sp, fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun QuickPlaySpellBooks(
) {
    val quickPlayViewModel: QuickPlayViewModel = viewModel()

    val possibleLevels by quickPlayViewModel.availableCharacterLevels.observeAsState(initial = emptyList())

    var selectedLevel by remember { mutableIntStateOf(0) }

    val selectedClass = quickPlayViewModel.currentClass?.let { Class.className(it) } ?: "UNIDENTIFIED"

    val quickPlaySpellList by quickPlayViewModel.currentQuickPlaySpellList.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            color = Color.Black.copy(alpha = 0.2F),
        )
        Text(
            text = selectedClass,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic
        )
        LazyRow {
            items(possibleLevels.size) { index ->
                    val level = possibleLevels[index]
                    LevelButton(
                        level,
                        onClick = {
                            selectedLevel = if (selectedLevel == level) 0 else level
                            quickPlayViewModel.updateCurrentCharacterLevel(level)

                            if (selectedLevel != 0) {
                                quickPlayViewModel.fetchQuickPlaySpellList()
                            } else {
                                quickPlayViewModel.clearSpellList()
                            }
                        },
                        selectedLevel = selectedLevel
                    )
                }
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6F)
                .background(
                    color = MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(20.dp)
                ), contentAlignment = Alignment.TopCenter
        ) {
            if (quickPlaySpellList.isNotEmpty()) {
                LazyColumn {
                    items(quickPlaySpellList.size) { index ->
                        QuickPlaySpellCard(spell = quickPlaySpellList[index])
                    }
                    item {
                        Divider(
                            modifier = Modifier.height(40.dp), color = Color.Transparent
                        )
                    }
                }
            } else {
                Text(
                    text = "Choose your character level",
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic
                    )
            }

        }
        Box(
            modifier = Modifier
                .weight(1F)
                .padding(10.dp)
        ) {
            ColouredButton(
                label = "Save spellbook",
                modifier = Modifier,
                color = ButtonDefaults.buttonColors(ButtonColors.GreenButton)
            ) {
                quickPlayViewModel.addToSpellBooks("")
            }
        }
        Spacer(modifier = Modifier.weight(1F))
    }
}

@Composable
fun LevelButton(
    level: Int, selectedLevel: Int, onClick: () -> Unit
) {
    val isSelected = level == selectedLevel

    Button(
        modifier = Modifier.padding(5.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) ButtonColors.SelectedButton
            else ButtonColors.UnselectedButton
        ),
        border = BorderStroke(
            width = 2.dp, color = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(5.dp),
    ) {
        Text(
            text = level.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun QuickPlaySpellCard(spell: Spell.SpellInfo) {
    val cardColor = MaterialTheme.colorScheme.secondary
    val images = SpellCardCreation(spell)
    Card(elevation = 10.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                GlobalOverlayState.currentSpell = spell
                GlobalOverlayState.showOverlay(OverlayType.LARGE_QUICKSPELLCARD)
            }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1F)
                ) {
                    Image(
                        painter = painterResource(id = images.schoolID),
                        contentDescription = "Spell school",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .border(
                                0.5.dp,
                                MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
                Box(
                    modifier = Modifier.weight(4F), contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = spell.name ?: "UNIDENTIFIED",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        maxLines = 1,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}
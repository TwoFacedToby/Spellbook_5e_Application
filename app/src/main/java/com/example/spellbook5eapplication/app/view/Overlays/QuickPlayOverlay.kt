package com.example.spellbook5eapplication.app.view.Overlays

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Model.Class
import com.example.spellbook5eapplication.app.view.screens.SaveSpellBookDialog
import com.example.spellbook5eapplication.app.view.spellCards.QuickPlaySpellCard
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.view.viewutilities.FadeSide
import com.example.spellbook5eapplication.app.view.viewutilities.LevelButton
import com.example.spellbook5eapplication.app.view.viewutilities.fadingEdge
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.QuickPlayViewModel
import com.example.spellbook5eapplication.ui.theme.ButtonColors

@Composable
fun QuickPlaySpellBooks() {
    val quickPlayViewModel: QuickPlayViewModel = viewModel()

    val possibleLevels by quickPlayViewModel.availableCharacterLevels.observeAsState(initial = emptyList())

    var selectedLevel by remember { mutableIntStateOf(0) }

    val selectedClass = quickPlayViewModel.currentClass?.let { Class.className(it) } ?: "UNIDENTIFIED"

    val quickPlaySpellList by quickPlayViewModel.currentQuickPlaySpellList.observeAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(GlobalOverlayState.getTopOverlay()) {
        if (!GlobalOverlayState.isOverlayVisible(OverlayType.LARGE_QUICKSPELLCARD)) {
            quickPlayViewModel.setPreventReset(false)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            quickPlayViewModel.resetValuesIfNeeded()
        }
    }

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
        val rowState = rememberLazyListState()
        LazyRow(
            state = rowState,
            modifier = Modifier
                .fadingEdge(
                    side = FadeSide.LEFT,
                    color = MaterialTheme.colorScheme.tertiary,
                    width = 20.dp,
                    isVisible = rowState.canScrollBackward,
                    spec = tween(500)
                )
                .fadingEdge(
                    side = FadeSide.RIGHT,
                    color = MaterialTheme.colorScheme.tertiary,
                    width = 20.dp,
                    isVisible = rowState.canScrollForward,
                    spec = tween(500)
                )
        ) {
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
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(20.dp)
                )
                .fadingEdge(
                    side = FadeSide.TOP,
                    color = MaterialTheme.colorScheme.tertiary,
                    width = 20.dp,
                    isVisible = true,
                    shape = RoundedCornerShape(20.dp)
                )
                .fadingEdge(
                    side = FadeSide.BOTTOM,
                    color = MaterialTheme.colorScheme.tertiary,
                    width = 20.dp,
                    isVisible = true,
                    shape = RoundedCornerShape(20.dp)
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

                showDialog = true
            }
        }
        Spacer(modifier = Modifier.weight(1F))
        if(showDialog){
            SaveSpellBookDialog {
                showDialog = false
            }
        }
    }
}

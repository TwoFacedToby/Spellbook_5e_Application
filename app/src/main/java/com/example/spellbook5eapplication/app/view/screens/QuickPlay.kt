package com.example.spellbook5eapplication.app.view.screens

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import android.graphics.Paint as AndroidPaint
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Class
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.view.spellCards.ClassCard
import com.example.spellbook5eapplication.app.view.spellCards.QuickPlaySpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellCardCreation
import com.example.spellbook5eapplication.app.view.viewutilities.BorderedText
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.view.viewutilities.FadeSide
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.view.viewutilities.fadingEdge
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.QuickPlayViewModel
import com.example.spellbook5eapplication.ui.theme.ButtonColors

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
                    .padding(top = 60.dp, bottom = 55.dp),
            ) {
                val listState = rememberLazyListState()
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .fadingEdge(
                            side = FadeSide.TOP,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6F),
                            width = 40.dp,
                            isVisible = listState.canScrollBackward,
                            spec = tween(500)
                        )
                        .fadingEdge(
                            side = FadeSide.BOTTOM,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6F),
                            width = 40.dp,
                            isVisible = listState.canScrollForward,
                            spec = tween(500),
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    val classes = Class.values()
                    items(classes.size) {
                        ClassCard(type = classes[it])
                    }
                    item { 
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
        OverlayRenderer(overlayStack = GlobalOverlayState.getOverlayStack())
    }
}
@Composable
fun SaveSpellBookDialog(onDissmiss: () -> Unit) {
    val quickPlayViewModel: QuickPlayViewModel = viewModel()
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var spellbookName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
        onDissmiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .size(350.dp, 250.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.large
                )
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.secondaryContainer,
                    MaterialTheme.shapes.large
                ),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box {
                    if (showError) {
                        Text(
                            text = errorMessage,
                            color = ButtonColors.RedButton,
                        )
                    }
                }
                Text(
                    text = "Name your spell book",
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 20.dp),
                    color = MaterialTheme.colorScheme.onTertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic
                    )
                UserInputField(
                    label = "spell book name",
                    onInputChanged = {
                        spellbookName = it
                    },
                    modifier = Modifier
                        .size(200.dp, 48.dp),
                    singleLine = true,
                    imeAction = ImeAction.Done
                )
                ColouredButton(
                    label = "OK",
                    modifier = Modifier
                        .padding(10.dp)
                        .height(48.dp),
                    color = ButtonDefaults.buttonColors(ButtonColors.GreenButton)
                ) {
                    if(spellbookName.isEmpty()){
                        errorMessage = "Your spell book must have a name"
                        showError = true
                    } else {
                        showError = false
                        quickPlayViewModel.addToSpellBooks(spellbookName)
                        onDissmiss()
                    }
                }
            }
        }
    }
}

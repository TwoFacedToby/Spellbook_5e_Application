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
import com.example.spellbook5eapplication.app.view.spellCards.SpellCardCreation
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
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
fun ClassCard(type: Class) {
    val quickPlayViewModel: QuickPlayViewModel = viewModel()
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .clickable {
                quickPlayViewModel.updateCurrentClass(type)
                GlobalOverlayState.showOverlay(OverlayType.QUICKPLAY_SPELLBOOK)
            },
        elevation = 20.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colorScheme.secondary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = Class.classBackground(type)),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alpha = 0.7F
            )
            BorderedText(text = Class.className(type))
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
        shape = MaterialTheme.shapes.extraSmall,
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
    val quickPlayViewModel: QuickPlayViewModel = viewModel()
    val cardColor = MaterialTheme.colorScheme.secondary
    val images = SpellCardCreation(spell)
    Card(elevation = 10.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                quickPlayViewModel.setPreventReset(true)
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

enum class FadeSide {
    LEFT, RIGHT, BOTTOM, TOP
}
fun Modifier.fadingEdge(
    side: FadeSide,
    color: Color,
    width: Dp,
    isVisible: Boolean,
    shape: Shape? = null,
    spec: AnimationSpec<Dp>? = null
): Modifier = composed {

    val animatedWidth = spec?.let {
        animateDpAsState(
            targetValue = if (isVisible) width else 0.dp,
            animationSpec = spec,
            label = "Fade width"
        ).value
    }

    val baseModifier = if (shape != null) this.then(clip(shape)) else this

    baseModifier.drawWithContent {
        drawContent()

            val (start, end) = when (side) {
                FadeSide.LEFT -> Offset.Zero to Offset(size.width, 0f)
                FadeSide.RIGHT -> Offset(size.width, 0f) to Offset.Zero
                FadeSide.BOTTOM -> Offset(0f, size.height) to Offset.Zero
                FadeSide.TOP -> Offset.Zero to Offset(0f, size.height)
            }

            val staticWidth = if (isVisible) width.toPx() else 0f

            val widthPx = animatedWidth?.toPx() ?: staticWidth

            val fraction: Float = when (side) {
                FadeSide.LEFT, FadeSide.RIGHT -> widthPx / size.width
                FadeSide.BOTTOM, FadeSide.TOP -> widthPx / size.height
            }

            drawRect(
                brush = Brush.linearGradient(
                    0f to color,
                    fraction to Color.Transparent,
                    start = start,
                    end = end
                ),
                size = size
            )
        }
    }

@Composable
fun BorderedText(text: String) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        drawTextWithBorder(text, Offset(100f, 100f))
    }
}

fun DrawScope.drawTextWithBorder(text: String, position: Offset) {
    val paint = AndroidPaint().apply {
        color = Color.Black.toArgb()
        textSize = 38.sp.toPx()
        strokeWidth = 8f
        style = android.graphics.Paint.Style.STROKE // For border
    }

    drawContext.canvas.nativeCanvas.drawText(text, position.x, position.y, paint)

    paint.apply {
        color = Color.White.toArgb()
        style = android.graphics.Paint.Style.FILL // For text itself
    }

    drawContext.canvas.nativeCanvas.drawText(text, position.x, position.y, paint)
}

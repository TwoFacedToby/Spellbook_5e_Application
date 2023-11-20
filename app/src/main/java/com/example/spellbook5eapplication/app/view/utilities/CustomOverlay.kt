package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.ui.geometry.Rect
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/*@Composable
fun CustomOverlay(
    globalOverlayState: GlobalOverlayState,
    overlayType: OverlayType,
    onDismissRequest: () -> Unit,
    content: @Composable (BoxScope.() -> Unit)? = null
) {
    if(globalOverlayState.getTopOverlay() == overlayType) {
        val screenHeight = LocalConfiguration.current.screenHeightDp
        val offsetY = remember { Animatable(screenHeight.toFloat()) }

        val scope = rememberCoroutineScope()

        val onDismiss: () -> Unit = {
            scope.launch {
                offsetY.animateTo(
                    targetValue = screenHeight.toFloat(),
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                onDismissRequest()
                globalOverlayState.dismissOverlay()
            }
        }

        LaunchedEffect(key1 = true) {
            offsetY.animateTo(
                (screenHeight.toFloat() / 5),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.black).copy(alpha = 0.2f))
                .offset(y = offsetY.value.dp)
        ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight.dp)
                        .background(
                            colorResource(id = R.color.main_color),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(5.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Log.d("CustomOverlay", "Is content null?: ${content == null}") // Logging statement
                    if (content != null) {
                        content()
                    } else {
                        DefaultContent(onDismiss)
                    }
                }
        }
    }
}*/

@Composable
fun DefaultContent(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hardcoded Title", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onDismiss) {
            Text("Hardcoded Button Text")
        }
    }
}

/*@Composable
fun CustomOverlay(
    globalOverlayState: GlobalOverlayState,
    overlayType: OverlayType,
    onDismissRequest: () -> Unit,
    content: @Composable (BoxScope.() -> Unit)? = null
) {
    if (globalOverlayState.getTopOverlay() == overlayType) {
        val screenHeight = LocalConfiguration.current.screenHeightDp
        val overlayHeight = screenHeight - screenHeight/6
        val offsetY = remember { Animatable(screenHeight.toFloat()) }

        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = true) {
            offsetY.animateTo(
                targetValue = screenHeight.toFloat() - overlayHeight.toFloat(),
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }

        val onDismiss: () -> Unit = {
            scope.launch {
                offsetY.animateTo(
                    targetValue = screenHeight.toFloat(),
                    animationSpec = tween(durationMillis = 300)
                )
                onDismissRequest()
                globalOverlayState.dismissOverlay()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = onDismiss)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(overlayHeight.dp)
                    .fillMaxWidth()
                    .offset { IntOffset(0, offsetY.value.roundToInt()) }
                    .background(
                        colorResource(id = R.color.main_color),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) { /* Prevent clicks from propagating to the parent Box */ }
                    .padding(5.dp)
                    /*.draggable(
                        state = rememberDraggableState { delta ->
                            val newValue = offsetY.value + delta
                            offsetY.value = newValue.coerceIn(screenHeight.toFloat() - overlayHeight.toFloat(), screenHeight.toFloat())
                        },
                        orientation = Orientation.Vertical,
                        onDragStopped = {
                            if (offsetY.value > screenHeight - overlayHeight / 2) {
                                onDismiss()
                            }
                        }
                    )*/,
                contentAlignment = Alignment.TopCenter
            ) {
                Log.d("CustomOverlay", "Is content null?: ${content == null}") // Logging statement
                if (content != null) {
                    content()
                } else {
                    DefaultContent(onDismiss)
                }
            }
        }
    }
}*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomOverlay(
    globalOverlayState: GlobalOverlayState,
    overlayType: OverlayType,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    if (globalOverlayState.getTopOverlay() == overlayType) {
        val screenHeight = LocalConfiguration.current.screenHeightDp
        val overlayHeight = screenHeight - screenHeight/6
        val swipeableState = rememberSwipeableState(initialValue = OverlaySwipeState.EXPANDED)

        val anchors = mapOf(
            screenHeight.toFloat() to OverlaySwipeState.COLLAPSED, // Fully collapsed at 0
            (screenHeight - overlayHeight).toFloat() to OverlaySwipeState.EXPANDED // Expanded position
        )

        val scope = rememberCoroutineScope()

        if (swipeableState.currentValue == OverlaySwipeState.COLLAPSED) {
            LaunchedEffect(swipeableState.currentValue) {
                onDismissRequest()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(onClick = {
                    scope.launch {
                        swipeableState.animateTo(OverlaySwipeState.COLLAPSED)
                    }
                })
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(overlayHeight.dp)
                    .fillMaxWidth()
                    .background(
                        colorResource(id = R.color.main_color),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() } // This absorbs the click event
                    ) { }
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        orientation = Orientation.Vertical,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) } // Adjust swipe sensitivity if needed
                    )
                    .padding(5.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Log.d("CustomOverlay", "Is content null?: ${content == null}") // Logging statement
                if (content != null) {
                    content()
                } else {
                    DefaultContent(onDismissRequest)
                }
            }
        }
    }
}

enum class OverlaySwipeState {
    EXPANDED, COLLAPSED
}


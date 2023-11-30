package com.example.spellbook5eapplication.app.view.utilities

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomOverlay(
    globalOverlayState: GlobalOverlayState,
    overlayType: OverlayType,
    onDismissRequest: () -> Unit,
    content: @Composable (() -> Unit)? = null
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val overlayHeight = (screenHeight / 5) * 4
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { overlayHeight.toPx() }
    val anchors = mapOf(0F to 0, sizePx to 1)

    if(globalOverlayState.getTopOverlay() == overlayType) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.black).copy(alpha = 0.2f))
                .clickable(
                    onClick = {
                        onDismissRequest()
                    }
                )
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Vertical,
                    thresholds = { _, _ -> FractionalThreshold(0.9f) }
                )
        ) {
            LaunchedEffect(swipeableState.currentValue) {
                if (swipeableState.currentValue == 1) {
                    onDismissRequest()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(overlayHeight)
                    .align(Alignment.BottomCenter)
                    .offset(y = swipeableState.offset.value.dp)
                    .background(
                        colorResource(id = R.color.main_color),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { /*Leaving it empty so that it consume the outer box' click event*/ }
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
                    if (content != null) {
                        content()
                    } else {
                        DefaultContent(onDismiss)
                    }
                }
        }
    }
}*/
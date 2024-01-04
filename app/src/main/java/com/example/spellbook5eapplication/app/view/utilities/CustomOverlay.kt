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
    overlayType: OverlayType,
    content: @Composable (() -> Unit)
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val overlayHeight = (screenHeight / 5) * 4

    if(GlobalOverlayState.getTopOverlay() == overlayType) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.black).copy(alpha = 0.2f))
                .clickable(
                    onClick = {
                        GlobalOverlayState.dismissOverlay()
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(overlayHeight)
                    .align(Alignment.BottomCenter)
                    .background(
                        colorResource(id = R.color.main_color),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { /*Leaving it empty so that it consume the outer box' click event*/ }
                    .padding(5.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                content()
            }
        }
    }
}
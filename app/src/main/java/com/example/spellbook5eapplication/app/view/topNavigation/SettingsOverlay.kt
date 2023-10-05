package com.example.spellbook5eapplication.app.view.topNavigation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import kotlinx.coroutines.launch

@Composable
fun SettingsOverlay(onDismissRequest: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val offsetY = remember { Animatable(screenHeight.toFloat()) }

    val scope = rememberCoroutineScope()

    val onDismiss: () -> Unit = {
        scope.launch {
            offsetY.animateTo(
                targetValue = screenHeight.toFloat(),
                animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow)
            )
            onDismissRequest()
        }
    }

    LaunchedEffect(key1 = true) {
        offsetY.animateTo((screenHeight.toFloat() / 5), animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow))
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
                    colorResource(id = R.color.primary_dark),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Settings", color = colorResource(id = R.color.white), fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }
        }
    }
}
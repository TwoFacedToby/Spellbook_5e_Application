package com.example.spellbook5eapplication.app.view.utilities

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
fun CustomOverlay(
    onDismissRequest: () -> Unit,
    content: @Composable (BoxScope.() -> Unit)? = null
) {
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
        offsetY.animateTo((screenHeight.toFloat() / 5),
            animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow))
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
                Log.d("CustomOverlay", "Is content null?: ${content == null}") // Logging statement
                if(content != null) {
                    content()
                } else {
                    DefaultContent(onDismiss)
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
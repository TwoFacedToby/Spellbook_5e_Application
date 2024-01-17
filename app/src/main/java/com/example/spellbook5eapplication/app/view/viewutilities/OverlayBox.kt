package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R

@Composable
fun OverlayBox(
    content : LazyListScope.() -> Unit){
    Box(
        modifier = Modifier
            .height(480.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.TopCenter
    ){
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .fadingEdge(
                    side = FadeSide.TOP,
                    color = MaterialTheme.colorScheme.primary,
                    width = 40.dp,
                    isVisible = lazyListState.canScrollBackward,
                    spec = tween(500)
                )
                .fadingEdge(
                    side = FadeSide.BOTTOM,
                    color = MaterialTheme.colorScheme.primary,
                    width = 40.dp,
                    isVisible = lazyListState.canScrollForward,
                    spec = tween(500)
                )
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }

}
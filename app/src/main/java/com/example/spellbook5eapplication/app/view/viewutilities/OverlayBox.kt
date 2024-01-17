package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
            .height(380.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(20.dp)
            ),
        contentAlignment = Alignment.TopCenter
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }

}
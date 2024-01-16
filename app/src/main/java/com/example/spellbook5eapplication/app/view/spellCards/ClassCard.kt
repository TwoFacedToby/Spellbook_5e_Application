package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Model.Class
import com.example.spellbook5eapplication.app.view.viewutilities.BorderedText
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.QuickPlayViewModel

@Composable
fun ClassCard(type: Class) {
    val quickPlayViewModel: QuickPlayViewModel = viewModel()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 10.dp, end = 10.dp)
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
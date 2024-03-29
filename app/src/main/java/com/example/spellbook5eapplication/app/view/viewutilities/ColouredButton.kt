package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColouredButton(
    label : String,
    modifier : Modifier = Modifier.height(48.dp),
    color : ButtonColors,
    onClick: () -> Unit
){
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = color,
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

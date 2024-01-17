package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.ui.theme.ButtonColors

@Composable
fun LevelButton(
    level: Int, selectedLevel: Int, onClick: () -> Unit
) {
    val isSelected = level == selectedLevel

    Button(
        modifier = Modifier.padding(5.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.onTertiaryContainer
            else MaterialTheme.colorScheme.tertiaryContainer
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
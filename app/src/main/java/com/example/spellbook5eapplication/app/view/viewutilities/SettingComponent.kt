package com.example.spellbook5eapplication.app.view.viewutilities

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.sp

@Composable
fun SettingComponent(name : String, boolean: Boolean, onClickRequest:() -> Unit) {
    Log.d("SettingsComp", "$name : $boolean")
    // State to hold the current value of the Switch
    val isChecked = remember { mutableStateOf(boolean) }

    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        Text(
            text = name,
            color = Color.White,
            fontSize = 18.sp
        )
        Switch(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                onClickRequest()
            },
            // Customize colors if needed
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Green,
                uncheckedThumbColor = Color.Gray,
                checkedTrackColor = Color.Green.copy(alpha = 0.5f),
                uncheckedTrackColor = Color.Gray.copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .graphicsLayer(
                    scaleX = 1.2f,
                    scaleY = 1.2f
                ),
        )
    }
}

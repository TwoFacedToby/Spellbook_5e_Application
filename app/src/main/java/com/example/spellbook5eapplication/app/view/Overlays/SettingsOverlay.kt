package com.example.spellbook5eapplication.app.view.Overlays

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.viewutilities.SettingComponent
import com.example.spellbook5eapplication.app.viewmodel.SettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Utility.CurrentSettings

@Composable
fun SettingsOverlay(onDismissRequest: () -> Unit) {

    val settingsViewModel: SettingsViewModel = viewModel()
    var currentSettings = settingsViewModel.currentSettings.value!!

    Column(
    modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween

) {
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp)),
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Settings", color = colorResource(id = R.color.white), fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        currentSettings = CurrentSettings.currentSettings
        LazyColumn(
            Modifier.fillMaxHeight(0.8f).padding(top = 16.dp)
        ){
            item{
                SettingComponent(name = "Save All Spells Locally",
                    currentSettings.saveSpellData
                ) {
                    val newSettings = currentSettings.copy()
                    newSettings.saveSpellData = !newSettings.saveSpellData
                    settingsViewModel.updateSettings(newSettings)
                }
                SettingComponent(name = "Use Internet",
                    currentSettings.useInternet
                ) {
                    val newSettings = currentSettings.copy()
                    newSettings.useInternet = !newSettings.useInternet
                    settingsViewModel.updateSettings(newSettings)
                }

            }
        }


        Button(
            onClick = {
            settingsViewModel.resetSettings()
            onDismissRequest()
            },colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red.copy(alpha = 0.5f),
                contentColor = Color.White
            )
            ) {
            Text("Reset All Settings")
        }

}
}
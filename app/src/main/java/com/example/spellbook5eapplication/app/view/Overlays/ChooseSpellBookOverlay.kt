package com.example.spellbook5eapplication.app.view.Overlays

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.utilities.OverlayBox
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
import com.example.spellbook5eapplication.app.view.utilities.CreateDialog

@Composable
fun ChooseSpellBookOverlay(
    onDismissRequest: () -> Unit,
    listOfStrings : List<String>? = null
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .clickable { onDismissRequest() },
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Choose Spellbook",
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OverlayBox(
            content = {
                if (listOfStrings != null) {
                    items(listOfStrings) { string ->
                        Text(
                            text = string,
                            modifier = Modifier
                                .clickable { /* TODO */ }
                                .padding(15.dp)
                        )
                    }
                } else {
                    items(1) {
                        Text(
                            text = "You have not created any spellbooks yet",
                            fontWeight = FontWeight.Bold,
                            color = colorResource
                                (id = R.color.white),
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        ColouredButton(
            label = "Create new spellbook",
            modifier = Modifier,
            color = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_button)),
            onClick = { showDialog = true})

        if (showDialog) {
            CreateDialog(onDismissRequest = { showDialog = false })
        }
    }
}

@Preview
@Composable
fun ChooseSpellBookOverlayPreview(){
    ChooseSpellBookOverlay(onDismissRequest = {
        println("Dismiss button clicked")
    })
}
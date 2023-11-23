package com.example.spellbook5eapplication.app.view.Overlays

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookViewModel
import com.example.spellbook5eapplication.app.Utility.SpellbookViewModelFactory
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
import com.example.spellbook5eapplication.app.view.utilities.CreateDialog




@Composable
fun AddToSpellBookOverlay(
    onDismissRequest: () -> Unit,
) {
    //Initializing viewModel to make the app recompose when a new spellbook is selected.
    val viewModel: SpellbookViewModel = viewModel(
        factory = SpellbookViewModelFactory(SpellController, SpelllistLoader)
    )

    val spellbooks = viewModel.spellbooks
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
            text = "Add to spellbook",
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OverlayBox(
            content = {
                if (spellbooks != null) {
                    items(spellbooks) { string ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = string.spellbookName,
                                modifier = Modifier.padding(15.dp),
                                color = colorResource(id = R.color.white)
                            )
                            IconButton(
                                onClick = { /* TODO */ }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = "Add to spellbook",
                                    tint = colorResource(id = R.color.spellcard_button),
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                        }
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
fun SpellBookOverlayPreview(){
    AddToSpellBookOverlay(onDismissRequest = {
        println("Dismiss button clicked")
    })
}
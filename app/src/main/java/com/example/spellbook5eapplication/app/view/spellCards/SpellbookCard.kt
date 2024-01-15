package com.example.spellbook5eapplication.app.view.spellCards

import SpellQueryViewModel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.MainViewModel
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.TitleState
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SpellbookCard(
    spellbook: Spellbook
) {

    var isEditing by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf(spellbook.spellbookName) }
    var oldTitle by remember { mutableStateOf(spellbook.spellbookName) }

    val onLongPress = {
        oldTitle = spellbook.spellbookName
        isEditing = true
    }

    val onTitleChangeConfirm = {
        if (newTitle.isNotBlank() && newTitle != oldTitle) {
            // Update the title of the spellbook
            spellbook.spellbookName = newTitle
            SpellbookManager.removeSpellbook(oldTitle)
            SpellbookManager.saveSpellbookToFile(newTitle)
        }
        isEditing = false
    }

    val spellbookImage = SpellbookCardCreation(spellbook)
    val spellQueryViewModel: SpellQueryViewModel = viewModel()

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.spellcard_color)),
        modifier = Modifier
            .height(150.dp)
            .padding(10.dp)
            .clickable {
                TitleState.currentTitle.value = spellbook.spellbookName
                spellQueryViewModel.loadSpellsFromSpellbook(spellbook)
            }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(spellbookImage.spellbookImageID),
                contentDescription = "Spellbook Image",
                modifier = Modifier
                    .weight(1f) // Adjust weight as needed
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .weight(2f) // Adjust weight as needed
                    .align(Alignment.CenterVertically)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isEditing) {
                    TextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        modifier = Modifier
                            .pointerInput(Unit) { detectTapGestures(onPress = { onLongPress() }) }
                            .onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                                    onTitleChangeConfirm()
                                    true // Event consumed
                                } else {
                                    false // Event not consumed
                                }
                            },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = spellbook.spellbookName,
                        modifier = Modifier
                            .fillMaxHeight(0.4f)
                            .padding(top = 16.dp)
                            .pointerInput(Unit) {
                            detectTapGestures(onLongPress = { onLongPress() })
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.black)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp)) // Space between name and description
                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(0.dp, 3.dp)
                )
                Spacer(modifier = Modifier.height(2.dp)) // Space between name and description

                Text(
                    modifier = Modifier.fillMaxHeight(0.8f),
                    text = spellbook.description, // Assuming 'description' is a property of Spellbook
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black),
                )

            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxHeight()
                    .padding(end = 4.dp, bottom = 4.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                IconButton(
                    onClick = {
                        SpellbookManager.removeSpellbook(spellbook.spellbookName)
                        spellQueryViewModel.loadSpellBooks()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete Spellbook",
                        modifier = Modifier
                            .size(24.dp),
                        tint = colorResource(id = R.color.spellcard_button)
                    )
                }
            }
        }
    }

}
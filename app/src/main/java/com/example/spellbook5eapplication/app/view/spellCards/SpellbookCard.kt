package com.example.spellbook5eapplication.app.view.spellCards

import SpellQueryViewModel
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@Composable
fun SpellbookCard(
    spellbook: Spellbook
) {

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
                Text(
                    text = spellbook.spellbookName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.black),

                )
                Spacer(modifier = Modifier.height(4.dp)) // Space between name and description
                Text(
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
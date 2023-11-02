package com.example.spellbook5eapplication.app.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellCardOverlay
import com.example.spellbook5eapplication.app.view.utilities.CreateButton
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme


@Composable
fun BrewScreen(){

    var showSpellbookOverlay by remember { mutableStateOf(false) }
    var showSpellDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.search_view_background),
                contentDescription = "Search view background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                alpha = 0.5F
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 100.dp, end = 10.dp, bottom = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    UserInputField(label = "Search")
                    Spacer(modifier = Modifier.width(5.dp))
                    FilterButton()
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    //Spacer(modifier = Modifier.heigth(5.dp))
                    CreateButton()
                }
                //TODO insert the lazy column for seacrh results
                SpellCard(onDialogRequest = {showSpellDialog = true}, onOverlayRequest = {showSpellbookOverlay = true})

                if(showSpellDialog){
                    SpellCardOverlay(
                        isSpellbookOverlayVisible = showSpellbookOverlay,
                        onToggleSpellbookOverlay = { showSpellbookOverlay = !showSpellbookOverlay },
                        onDismissRequest = { showSpellDialog = false })
                }
                if(showSpellbookOverlay){
                    /*
                    SpellBookOverlay(
                        isSpellbookOverlayVisible = showSpellbookOverlay,
                        onDismissRequest = {showSpellbookOverlay = false})
               */
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrewScreenview() {
    Spellbook5eApplicationTheme {
        BrewScreen()
    }
}
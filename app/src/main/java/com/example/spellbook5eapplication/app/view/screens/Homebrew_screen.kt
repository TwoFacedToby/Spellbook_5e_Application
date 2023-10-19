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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellCardOverlay
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField

@Composable
fun HomebrewScreen(){

    CreateSpell(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))

/* Old placeholder
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Homebrew screen")
    }

     */
}




@Composable
fun CreateSpell(modifier: Modifier = Modifier) {
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
                // Write name of spell
                UserInputField(label = "Name")
                Spacer(modifier = Modifier.width(5.dp))

            }
            //Save the spell
            Button(
                onClick = { /* TO DO */ },
            ) {
                Text(text = "Save spell", fontSize = 12.sp)
            }
        }
    }
}}

/*
@Composable
fun CreateSpell(modifier: Modifier = Modifier) {
    var name by remember {mutableStateOf("")}
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    //Write name here
    {
        UserInputField(label = "name")
        Spacer(modifier = Modifier.width(5.dp))
    }
    //Save spell by pressing button
    Button(
        onClick = { /* TO DO */ },
    ) {
        Text(text = "Save spell", fontSize = 24.sp)
    }
}

 */

@Preview
@Composable
fun SimpleComposablePreview() {
    HomebrewScreen()
}
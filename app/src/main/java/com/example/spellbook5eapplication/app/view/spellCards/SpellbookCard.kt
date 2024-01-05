package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Spellbook

@Composable
fun SpellbookCard(
    spellbook: Spellbook, // Assuming Spellbook has a property `name`
    //onSpellbookCardRequest: () -> Unit // Callback when the card is clicked
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.spellcard_color)),
        modifier = Modifier
            .height(150.dp)
            .padding(10.dp)
            //.clickable { onSpellbookCardRequest() }
    ) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .width(290.dp)
                .padding(10.dp)
        ) {
            Text(
                text = spellbook.spellbookName,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = colorResource(id = R.color.black)
            )
            // You can add more content here if needed, like a list of spell names, etc.
        }
    }
}
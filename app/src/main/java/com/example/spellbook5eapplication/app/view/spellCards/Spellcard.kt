package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.topNavigation.SettingsOverlay

@Composable
fun SpellCard(onDialogRequest: () -> Unit, onOverlayRequest: () -> Unit) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.primary_white)),
        modifier = Modifier
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .height(140.dp)
                .width(290.dp)
        )
        {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 2.5.dp)
                    .fillMaxWidth()
                    .height(36.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top
            )
            {
                Image(
                    painter = painterResource(id = R.drawable.abjuration),
                    contentDescription = "Spell school",
                    modifier = Modifier
                        .size(35.dp, 35.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .border(
                            0.5.dp,
                            colorResource(id = R.color.secondary_dark),
                            shape = RoundedCornerShape(2.dp)
                        )
                )
                Column(
                    modifier = Modifier.width(145.dp)
                ) {
                    Text(
                        text = "Spell name here",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(5.dp, 0.dp)
                    )
                    Divider(
                        color = colorResource(id = R.color.black),
                        thickness = 1.dp,
                        modifier = Modifier.padding(5.dp, 0.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.druid2),
                            contentDescription = "Class",
                            modifier = Modifier
                                .size(16.dp, 16.dp)
                                .padding(1.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .shadow(elevation = 5.dp)
                        )
                    }
                }
                IconButton(
                    onClick = { onOverlayRequest() }) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add to spellbook",
                        tint = colorResource(id = R.color.border_color_dark)
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite button",
                        tint = colorResource(id = R.color.border_color_dark)
                    )
                }
            }

            SpellInfo()

            Row(
                modifier = Modifier
                    .padding(end = 10.dp, top = 0.dp, bottom = 10.dp, start = 10.dp)
                    .fillMaxWidth(),
            )
            {
                Spacer(modifier = Modifier.width(228.dp))
                IconButton(onClick = { onDialogRequest() }) {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Expand button",
                        tint = colorResource(id = R.color.border_color_dark)
                    )
                }
            }
        }
    }
}

@Composable
fun SpellInfo(){
    Row(
        modifier = Modifier
            .padding(top = 2.5.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    )
    {
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "Level:", fontSize = 10.sp, maxLines = 1)
            Text(text = "<text>", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.border_color_dark))
            Text(text = "Range:", fontSize = 10.sp, maxLines = 1)
            Text(text = "<text>", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.border_color_dark))
        }
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "School:", fontSize = 10.sp, maxLines = 1)
            Text(text = "<text>", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.border_color_dark))
            Text(text = "Duration:", fontSize = 10.sp, maxLines = 1)
            Text(text = "<text>", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.border_color_dark))
        }
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "Casting Time:", fontSize = 10.sp, maxLines = 1)
            Text(text = "<text>", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.border_color_dark))
            Text(text = "Components:", fontSize = 10.sp, maxLines = 1)
            Text(text = "<text>", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.border_color_dark))
        }
    }
}


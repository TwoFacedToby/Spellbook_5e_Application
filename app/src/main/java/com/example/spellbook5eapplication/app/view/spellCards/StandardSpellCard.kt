package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell

@Composable
fun StandardSpellCard(
    spell: Spell,
    isHomeBrew: Boolean,
    onFullSpellCardRequest: () -> Unit,
    addButtonComposable: @Composable () -> Unit,
    favoriteButtonComposable: @Composable () -> Unit
) {

    val cardColor = R.color.spellcard_color

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = cardColor)),
        modifier = Modifier
            .height(150.dp)
            .padding(10.dp)
            .clickable {
                onFullSpellCardRequest()
            }
    ) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .width(290.dp)
        ) {
            Row {
                Column(
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 2.5.dp)
                            .width(220.dp)
                            .height(36.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.Top
                    ) {
                        Image(
                            painter = painterResource(id = spell.schoolImage),
                            contentDescription = "Spell school",
                            modifier = Modifier
                                .size(35.dp, 35.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .border(
                                    0.5.dp,
                                    colorResource(id = R.color.border_color),
                                    shape = RoundedCornerShape(2.dp)
                                )
                        )

                        Column(
                            modifier = Modifier.width(145.dp)
                        ) {
                            val spellName = spell.name
                            Text(
                                text = spellName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                maxLines = 1,
                                color = colorResource(id = R.color.black),
                                modifier = Modifier.padding(5.dp, 0.dp)
                            )

                            Divider(
                                color = colorResource(id = R.color.black),
                                thickness = 1.dp,
                                modifier = Modifier.padding(5.dp, 0.dp)
                            )

                            val lazyListState = rememberLazyListState()

                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(4.dp, 0.dp),
                                horizontalArrangement = Arrangement.Start,
                                state = lazyListState

                            ) {
                                spell.classImages?.let {
                                    items(it.size) { index ->
                                        Image(
                                            painter = painterResource(id = spell.classImages!![index]),
                                            contentDescription = "Class",
                                            modifier = Modifier
                                                .size(16.dp, 16.dp)
                                                .padding(1.dp)
                                                .clip(RoundedCornerShape(2.dp))
                                                .shadow(elevation = 5.dp)
                                        )
                                    }
                                }
                            }
                        }
                        SpellInfo(spell)
                    }
                    Column(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        addButtonComposable()

                        favoriteButtonComposable()
                    }
                }
            }
        }
    }
}

@Composable
fun SpellInfo(spell : Spell){
    val size = 12.sp
    val lines = 1
    val headerColor = colorResource(id = R.color.unselected_icon)
    val fontColor = colorResource(id = R.color.black)
    Row(
        modifier = Modifier
            .padding(top = 2.5.dp, start = 10.dp, end = 10.dp, bottom = 0.dp)
            .width(220.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    )
    {
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "Level:", fontSize = size, maxLines = lines, color = headerColor)
            val spellLevel = "$spell.level"
            Text(text = spellLevel, fontSize = size, maxLines = lines, color = fontColor)
            Text(text = "Range:", fontSize = size, maxLines = lines, color = headerColor)
            val spellRange = spell.range
            Text(text = spellRange, fontSize = size, maxLines = lines, color = fontColor)
        }
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "School:", fontSize = size, maxLines = lines, color = headerColor)
            val spellSchool = spell.school
            Text(text = spellSchool, fontSize = size, maxLines = lines, color = fontColor)
            Text(text = "Duration:", fontSize = size, maxLines = lines, color = headerColor)
            val spellDuration = spell.duration
            Text(text = spellDuration, fontSize = size, maxLines = lines, color = fontColor)
        }
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "Casting Time:", fontSize = size, maxLines = lines, color = headerColor)
            val spellCT = spell.casting_time
            Text(text = spellCT, fontSize = size, maxLines = lines, color = fontColor)
            Text(text = "Components:", fontSize = size, maxLines = lines, color = headerColor)
            var spellComponents = ""

                for(index in 1..spell.components.size){
                    if(index - 1 != 0) spellComponents += ", "
                    spellComponents += spell.components[index-1]
                }

            Text(text = spellComponents, fontSize = size, maxLines = lines, color = fontColor)
        }
    }
}
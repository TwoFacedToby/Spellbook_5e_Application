package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpellCard(
    info : Spell_Info.SpellInfo,
    onFullSpellCardRequest: () -> Unit,
    onAddToSpellbookRequest: () -> Unit
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.spellcard_color)),
        modifier = Modifier
            .padding(10.dp)
            .combinedClickable(
                onClick = { onFullSpellCardRequest() },
                onLongClick = { /* TODO  delete option*/ }
            )
            /*.clickable { onFullSpellCardRequest() }*/
    ) {
        Column(
            modifier = Modifier
                .height(120.dp)
                .width(290.dp)
        )
        {
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
                    )
                    {
                        SchoolImage(info = info,
                            modifier =Modifier
                            .size(35.dp, 35.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .border(
                                0.5.dp,
                                colorResource(id = R.color.border_color),
                                shape = RoundedCornerShape(2.dp)
                            ))
                        Column(
                            modifier = Modifier.width(145.dp)
                        ) {
                            Text(
                                text = info.name ?: "NA",
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
                                info.classes?.forEach { classes ->
                                    ClassImage(className = classes.name)
                                }

                            }
                        }
                    }
                    SpellInfo(info)
                }
                Column(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    IconButton(
                        onClick = { onAddToSpellbookRequest() }) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "Add to spellbook",
                            tint = colorResource(id = R.color.spellcard_button),
                            modifier = Modifier.size(35.dp)
                        )
                    }

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite button",
                            tint = colorResource(id = R.color.spellcard_button),
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }
            }
        }
        }
    }

@Composable
fun SpellInfo(
    info : Spell_Info.SpellInfo
){
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
            Text(text = "Level:", fontSize = 10.sp, maxLines = 1)
            Text(text = if(info.level != null) info.level.toString() else "NA", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.spellcard_text))
            Text(text = "Range:", fontSize = 10.sp, maxLines = 1)
            Text(text = info.range?:"NA", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.spellcard_text))
        }
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "School:", fontSize = 10.sp, maxLines = 1)
            Text(text = info.school?.name ?:"NA", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.spellcard_text))
            Text(text = "Duration:", fontSize = 10.sp, maxLines = 1)
            Text(text = info.duration?:"NA", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.spellcard_text))
        }
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Text(text = "Casting Time:", fontSize = 10.sp, maxLines = 1)
            Text(text = info.castingTime?:"NA", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.spellcard_text))
            Text(text = "Components:", fontSize = 10.sp, maxLines = 1)
            if(info.components?.isEmpty() == true){
                Text(text = "", fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.spellcard_text))
            } else {
                Row {
                    info.components?.forEach{
                        Text(text = it, fontSize = 10.sp, maxLines = 1, color = colorResource(id = R.color.spellcard_text))
                    }
                }
            }
        }
    }
}

@Composable
fun SchoolImage(info: Spell_Info.SpellInfo, modifier: Modifier) {
    val imageResource = SchoolMappings.getDrawableResourceForSchool(info.school?.name)
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "Spell school",
        modifier = modifier
    )
}

@Composable
fun ClassImage(className: String?){
    val imageResource = ClassMappings.getDrawableResourceForClass(className)
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "Class",
        modifier = Modifier
            .size(16.dp, 16.dp)
            .padding(1.dp)
            .clip(RoundedCornerShape(2.dp))
            .shadow(elevation = 5.dp)
    )
}

/*@Preview
@Composable
fun PreviewSpellCard() {
            SpellCard(
                onFullSpellCardRequest = { println("Full spellcard request") },
                onAddToSpellbookRequest = { println("Add to spellbook request") }
            )
        }*/


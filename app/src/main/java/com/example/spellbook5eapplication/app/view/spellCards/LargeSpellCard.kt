package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R


@Composable
fun SpellCardOverlay(
    onToggleSpellbookOverlay: () -> Unit,
    onDismissRequest: () -> Unit)
{
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.primary_white)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .align(Alignment.Center)
                    .padding(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 0.dp, end = 10.dp, start = 10.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Spell name here",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(5.dp, 0.dp),
                            color = colorResource(id = R.color.black)
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth(Alignment.End),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { onToggleSpellbookOverlay() }) {
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

                            IconButton(
                                onClick = { onDismissRequest() }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Close",
                                    tint = colorResource(id = R.color.border_color_dark)
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 10.dp, end = 10.dp)
                    )
                    {
                        Divider(
                            color = colorResource(id = R.color.black),
                            thickness = 0.7.dp,
                            modifier = Modifier.padding(5.dp, 0.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .width(220.dp)
                                .padding(start = 10.dp, end = 10.dp)
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Spacer(modifier = Modifier.padding(start = 10.dp))
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

                            SpellInfo()
                        }

                        Column(
                            modifier = Modifier.padding(end = 15.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.abjuration),
                                contentDescription = "Spell school",
                                modifier = Modifier
                                    .size(60.dp, 60.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .border(
                                        0.5.dp,
                                        colorResource(id = R.color.secondary_dark),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        .weight(2f)
                ) {
                    Text(
                        text = "Spell description",
                        modifier = Modifier.padding(start = 10.dp),
                        color = colorResource(id = R.color.black)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom
                )
                {
                    Text(
                        text = "Tags:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp),
                        color = colorResource(id = R.color.black)
                    )
                    Divider(
                        color = colorResource(id = R.color.black),
                        thickness = 1.dp,
                        modifier = Modifier.padding(5.dp, 0.dp)
                    )
                }
            }
        }
    }

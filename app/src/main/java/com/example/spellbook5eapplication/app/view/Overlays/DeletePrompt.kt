package com.example.spellbook5eapplication.app.view.Overlays

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
@Composable
fun DeleteOverlay(onDismissRequest: () -> Unit, name: String, closeSpell: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    )
    {
        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 100.dp)
                .background(
                    color = colorResource(id = R.color.main_color),
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.border_color),
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = "Sure you want to delete this spell?",
                    fontSize = 15.sp,
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    ColouredButton(
                        "Cancel", modifier = Modifier, color = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = androidx.appcompat.R.color.material_grey_800
                            )
                        )
                    ) {
                        println("Button dismiss clicked")
                        onDismissRequest()
                    }

                    ColouredButton(
                        "Delete", modifier = Modifier, color = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.red_button
                            )
                        )
                    ) {
                        SpellController.deleteHomeBrew(name = name)
                        println("Button delete clicked")
                        onDismissRequest()
                        closeSpell()
                    }
                }
            }
        }
    }
}
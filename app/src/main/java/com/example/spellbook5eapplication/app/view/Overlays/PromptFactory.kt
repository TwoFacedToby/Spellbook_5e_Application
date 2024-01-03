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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton

class PromptFactory {

    @Composable
    fun Prompt(onDismissRequest: () -> Unit, message: String, redButFunc: () -> Unit, redButMessage: String, greyButMessage: String) {
        Dialog(
            onDismissRequest = { /*TODO*/ }
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
                        text = message,
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
                            greyButMessage, modifier = Modifier, color = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = androidx.appcompat.R.color.material_grey_800
                                )
                            )
                        ) {
                            println("Button: " + greyButMessage + " clicked")
                            onDismissRequest()
                        }

                        ColouredButton(
                            redButMessage , modifier = Modifier, color = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = R.color.red_button
                                )
                            )
                        ) {
                            redButFunc()
                            // Old delete homebrew
                            // SpellController.deleteHomeBrew(name = name)
                            println("Button:" + redButMessage + " clicked")
                            onDismissRequest()
                            // closeSpell()
                        }
                    }
                }
            }
        }
    }


    @Preview
    @Composable
    fun PreviewPrompt() {

        Prompt(
            onDismissRequest = { /*TODO*/ },
            message = "Testing prompt",
            redButFunc = { /*TODO*/ },
            redButMessage = "Red",
            greyButMessage = "Grey"
        )
    }

}
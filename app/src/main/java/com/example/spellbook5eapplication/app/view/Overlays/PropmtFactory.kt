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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.ui.theme.ButtonColors

@Composable
fun CreateOverlay(message: String, button1Message: String, button2Message: String, button2Function: () -> Unit) {
    Dialog(
        onDismissRequest = { /*TODO*/ }
    )
    {
        Box(
            modifier = Modifier
                .size(width = 350.dp, height = 100.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = message,
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    ColouredButton(
                        button1Message, modifier = Modifier
                            .height(38.dp)
                            .width(100.dp), color = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        GlobalOverlayState.dismissOverlay()
                    }

                    Spacer(modifier = Modifier.width(30.dp))

                    ColouredButton(
                        button2Message, modifier = Modifier
                            .height(38.dp)
                            .width(100.dp), color = ButtonDefaults.buttonColors(
                            containerColor = ButtonColors.RedButton
                        )
                    ) {
                        GlobalOverlayState.dismissOverlay()
                        button2Function()
                    }
                }
            }
        }
    }
}
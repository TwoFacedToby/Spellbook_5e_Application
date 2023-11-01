package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.spellbook5eapplication.R

@Composable
fun CreateDialog(
    onDismissRequest: () -> Unit
){
    Dialog(
        onDismissRequest = { /*TODO*/ }
    )
    {
        Box(
            modifier = Modifier
                .size(width = 250.dp, height = 150.dp)
                .background(
                    color = colorResource(id = R.color.main_color),
                    shape = RoundedCornerShape(15.dp)
                )
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.border_color),
                    shape = RoundedCornerShape(15.dp))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = "Create new Spellbook",
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Bold
                )
                UserInputField(
                    label = "Spellbook name",
                    onInputChanged = { input -> println("User input: $input") },
                    modifier = Modifier
                        .height(40.dp)
                        .width(200.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    ColouredButton(
                        label = "Cancel",
                        modifier = Modifier,
                        color = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red_button)),
                        onClick = { onDismissRequest() })
                    ColouredButton(
                        label = "Create",
                        modifier = Modifier,
                        color = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_button)),
                        onClick = { /* TODO */ })
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateDialogPreview(){
    CreateDialog {
        println("onDismiss")
    }
}
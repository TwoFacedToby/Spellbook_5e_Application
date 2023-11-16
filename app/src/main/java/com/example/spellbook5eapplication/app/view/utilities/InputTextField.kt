package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme

@Composable
fun UserInputField(
    label: String,
    onInputChanged: (String) -> Unit,
    modifier : Modifier,
    singleLine : Boolean
){
    var input by remember { mutableStateOf("") }
    //val coroutineScope = rememberCoroutineScope()

    BasicTextField(
        value = input,
        /*onValueChange = {
            input = it
            coroutineScope.launch {
                delay(300)  // Wait for 300ms
                if (input == it) {
                    onInputChanged(it)
                }
            }
        }*/
        onValueChange = {
            input = it
            onInputChanged(it)
        },
        modifier = modifier,
        singleLine = singleLine,
        cursorBrush = SolidColor(colorResource(id = R.color.white)),
        textStyle = LocalTextStyle.current.copy(color = colorResource(id = R.color.white)),
        decorationBox = {
                innerTextField ->
            Box(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.border_color),
                        shape = RoundedCornerShape(2.dp))
                    .background(
                        colorResource(id = R.color.main_color),
                        shape = RoundedCornerShape(2.dp))
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 5.dp),
                    verticalAlignment = Alignment.Top
                )
                {
                    Spacer(modifier = Modifier.width(4.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    if (input.isEmpty())
                        Text(
                            text = label,
                            style = LocalTextStyle.current.copy(
                                color = colorResource(id = R.color.border_color),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                    innerTextField()
                }
            }
        }
    )
}
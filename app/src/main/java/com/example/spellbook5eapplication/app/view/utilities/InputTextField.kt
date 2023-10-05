package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
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
fun UserInputField(label: String){
    var input by remember { mutableStateOf("") }

    BasicTextField(
        value = input,
        onValueChange = { input = it },
        modifier = Modifier.height(48.dp)
            .width(250.dp)
            .background(colorResource(id = R.color.secondary_dark), RoundedCornerShape(2.dp))
            .padding(2.dp),
        singleLine = true,
        cursorBrush = SolidColor(colorResource(id = R.color.white)),
        textStyle = LocalTextStyle.current.copy(color = colorResource(id = R.color.white)),
        decorationBox = {
                innerTextField ->
            Row(
                modifier = Modifier.width(250.dp)
                    . background(
                colorResource(id = R.color.primary_dark),
                    shape = RoundedCornerShape(2.dp)
            )
                .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically)
            {
                if(input.isEmpty())
                    Text(text = label,
                        style = LocalTextStyle.current.copy(
                            color = colorResource(id = R.color.secondary_dark),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp)
                    )
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InputField() {
    Spellbook5eApplicationTheme {
        UserInputField("Search")
    }
}
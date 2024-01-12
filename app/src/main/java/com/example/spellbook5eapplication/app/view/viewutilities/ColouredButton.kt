package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R

@Composable
fun ColouredButton(
    label : String,
    modifier : Modifier,
    color : ButtonColors,
    onClick: () -> Unit
){
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = color,
        border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.border_color)),
        shape = RoundedCornerShape(5.dp),
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun ColouredButtonPreview(){
    ColouredButton("Create", modifier = Modifier, color = ButtonDefaults.buttonColors(containerColor = colorResource(
        id =R.color.green_button
    ))){
        println("Button clicked")
    }
}
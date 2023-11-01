package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
fun GreenButton(
    label : String,
    onClick: () -> Unit
){
    Button(onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_button)),
        border = BorderStroke(width = 2.dp, color = colorResource(id = R.color.border_color)),
        shape = RoundedCornerShape(5.dp)
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
fun GreenButtonPreview(){
    GreenButton("Create"){
        println("Button clicked")
    }
}
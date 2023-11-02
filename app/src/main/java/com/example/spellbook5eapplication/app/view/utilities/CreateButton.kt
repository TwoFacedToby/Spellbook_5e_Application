package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme

@Composable
fun CreateButton(){
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.primary_dark),
                shape = RoundedCornerShape(2.dp))
            .border(BorderStroke(
                2.dp,
                colorResource(id = R.color.border_color_dark)),
                shape = RoundedCornerShape(2.dp)),
        colors = IconButtonDefaults.iconButtonColors(containerColor = colorResource(id = R.color.primary_dark), contentColor = colorResource(
            id = R.color.primary_white))
    )
    {
        Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
    }
}

@Preview(showBackground = true)
@Composable
fun IconButtonPreview() {
    Spellbook5eApplicationTheme {
        CreateButton()
    }
}
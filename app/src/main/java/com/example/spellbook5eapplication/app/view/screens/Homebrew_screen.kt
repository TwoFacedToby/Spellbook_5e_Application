package com.example.spellbook5eapplication.app.view.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellCardOverlay
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField

@Composable
fun HomebrewScreen(){

    CreateSpell(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))

/* Old placeholder
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "Homebrew screen")
    }

     */
}




@Composable
fun CreateSpell(modifier: Modifier = Modifier) {
Surface(
modifier = Modifier
.fillMaxSize()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.search_view_background),
            contentDescription = "Search view background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
            alpha = 0.5F
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, top = 100.dp, end = 10.dp, bottom = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                // Write name of spell
                UserInputField(label = "Name")
                Spacer(modifier = Modifier.width(5.dp))

            }

            //Space between name and description
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                //Write a description
                UserDescField(label = "Description")
                Spacer(modifier = Modifier.width(5.dp))
            }

            //Space between description and School
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                //Write a description
                UserInputField(label = "School")
                Spacer(modifier = Modifier.width(5.dp))
            }

            //Space between school and class
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
            {
                //Write a class name
                UserInputField(label = "Class")
                Spacer(modifier = Modifier.width(5.dp))
                //add class to a list
                AddClassButton()
            }
            //Save the spell
            Button(
                onClick = { /* TO DO */ },
            ) {
                Text(text = "Save spell", fontSize = 12.sp)
            }
        }
    }
}}

/*
@Composable
fun CreateSpell(modifier: Modifier = Modifier) {
    var name by remember {mutableStateOf("")}
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    //Write name here
    {
        UserInputField(label = "name")
        Spacer(modifier = Modifier.width(5.dp))
    }
    //Save spell by pressing button
    Button(
        onClick = { /* TO DO */ },
    ) {
        Text(text = "Save spell", fontSize = 24.sp)
    }
}

 */

//Field to write description in
@Composable
fun UserDescField(label: String){
    var input by remember { mutableStateOf("") }

    BasicTextField(
        value = input,
        onValueChange = { input = it },
        modifier = Modifier.height(192.dp)
            .width(250.dp)
            .background(colorResource(id = R.color.border_color_dark), RoundedCornerShape(2.dp))
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
                verticalAlignment = Alignment.Top)
            {
                if(input.isEmpty())
                    Text(text = label,
                        style = LocalTextStyle.current.copy(
                            color = colorResource(id = R.color.border_color_dark),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp)
                    )
                innerTextField()
            }
        }
    )
}

//Button to add class
@Composable
fun AddClassButton(){
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .background(
                color = colorResource(id = R.color.primary_dark),
                shape = RoundedCornerShape(2.dp)
            )
            .border(
                BorderStroke(
                2.dp,
                colorResource(id = R.color.border_color_dark)
                ),
                shape = RoundedCornerShape(2.dp)
            ),
        colors = IconButtonDefaults.iconButtonColors(containerColor = colorResource(id = R.color.primary_dark), contentColor = colorResource(
            id = R.color.primary_white)
        )
    )
    {
        Icon(imageVector = Icons.Outlined.Add, contentDescription = "")
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    HomebrewScreen()
}
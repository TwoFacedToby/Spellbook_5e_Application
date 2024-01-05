package com.example.spellbook5eapplication.app.view.Overlays

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField

@Composable
fun CreateSpellOverlay1(
    spell: Spell,
    previous: () -> Unit,
    first: Boolean,
    next: () -> Unit,
    last: Boolean,
    description: String,
    userChoise: @Composable () -> Unit
){
    //Utilities
    var showDialog = false
    var next by remember { mutableStateOf("Next") }
    if(last){
        next = "Create"
    }


    //

    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Testing visual
        Box(
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.overlay_box_color),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {

                // Back to old

                Spacer(modifier = Modifier.height(20.dp))

                // Top navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    if(!first){

                        ColouredButton(
                            label = "Previous",
                            modifier = Modifier,
                            color = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = R.color.selected_button
                                )
                            )
                        ) {
                            previous()
                        }}

                    Spacer(modifier = Modifier.width(10.dp))

                    ColouredButton(
                        "Cancel", modifier = Modifier, color = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.red_button
                            )
                        )
                    ) {
                        showDialog = true
                        println("Button Stop creation and delete when clicked")
                    }
                    if (showDialog) {
                        // Might want a PromptFactory in here instead
                        EraseOverlay(
                            onDismissRequest = { showDialog = false },
                            onEraseRequest = { /* TODO */ })
                    }


                    Spacer(modifier = Modifier.width(10.dp))


                    // Button to move on
                    ColouredButton(
                        next, modifier = Modifier, color = ButtonDefaults.buttonColors(
                            containerColor = colorResource(
                                id = R.color.green_button
                            )
                        )
                    ) {
                        next()
                    }
                }
                //End of top navigation

                Spacer(modifier = Modifier.height(10.dp))

                // Ways for users to give input

                userChoise()

                // End of ways for users to give input

                Spacer(Modifier.height(10.dp))

                Text(
                    text = description,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }

}

@Composable
fun UsersInputNDrop1(
    input: String,
    inputChange: (String) -> Unit,
    singleLineInput: Boolean,
    dropdown: List<String>,
    dropName: String,
    dropChange: (String) -> Unit
) {

    var expand by remember { mutableStateOf(false) }
    var current by remember { mutableStateOf(dropName) }
    var selectedDropdownItem by remember { mutableStateOf(input) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {


        UserInputField(
            label = "name",
            //Should connect with name
            onInputChanged = { input ->
                run { inputChange(input) }

            },
            modifier = Modifier
                .size(width = 220.dp, height = 48.dp),
            singleLine = singleLineInput,
            imeAction = ImeAction.Default,
            //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
        )

    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        //If a dropdownbar is needed (I am in great doubt if this will work)



        // Button to open dropdown
        ColouredButton(
            label = "$current",
            modifier = Modifier,
            color = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.selected_button
                )
            )
        )
        { expand = true }

    }


    Spacer(modifier = Modifier.height(5.dp))

    if (expand) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = colorResource(id = R.color.overlay_box_color))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dropdown.forEach { item ->
                    Text(
                        text = item,
                        color = colorResource(id = R.color.white),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedDropdownItem = item
                                dropChange(item)
                                current = item
                                expand = false
                            }
                            .padding(vertical = 5.dp) // Size of each dropdown item
                    )
                    Divider(
                        //color = R.color.selected_button,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun UsersInputOnly1(
    input: String,
    inputChange: (String) -> Unit,
    singleLineInput: Boolean,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {


        UserInputField(
            label = "name",
            //Should connect with name
            onInputChanged = { input ->
                run { inputChange(input) }

            },
            modifier = Modifier
                .size(width = 220.dp, height = 48.dp),
            singleLine = singleLineInput,
            imeAction = ImeAction.Default,
            //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
        )

    }}



@Composable
fun UserDropOnly1(
    dropdown: List<String>,
    dropName: String,
    dropChange: (String) -> Unit
) {

    var expand by remember { mutableStateOf(false) }
    var current by remember { mutableStateOf(dropName) }
    var selectedDropdownItem by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {


        // Button to open dropdown
        ColouredButton(
            label = "$current",
            modifier = Modifier,
            color = ButtonDefaults.buttonColors(
                containerColor = colorResource(
                    id = R.color.selected_button
                )
            )
        )
        { expand = true }
    }

    Spacer(modifier = Modifier.height(5.dp))

    if (expand) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = colorResource(id = R.color.overlay_box_color))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dropdown.forEach { item ->
                    Text(
                        text = item,
                        color = colorResource(id = R.color.white),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedDropdownItem = item
                                dropChange(item)
                                current = item
                                expand = false
                            }
                            .padding(vertical = 5.dp) // Size of each dropdown item
                    )
                    Divider(
                        //color = R.color.selected_button,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }}}}}



// Preview moslty for testing
@Preview
@Composable
fun previewCreatingPage1() {

    val spell = Spell().apply {
        name = "Test spell"
        level = 1
    }

    val levels = listOf("1", "2", "3")

// Test with of inputfield
    /*
        CreateSpellOverlay1(
            spell = spell,
            previous = { /*TODO*/ },
            first = false,
            next = { /*TODO*/ },
            last = false,
            description = "This is a test\n I hope this here will work",
            userChoise = {
                UsersInputOnly1(
                    input = spell.name,
                    inputChange = {
                        spell.name = it
                        println("Name set to ${spell.name}")
                    },
                    singleLineInput = true,
                )
            }
        )
    */

    //Test of dropdown menu

    CreateSpellOverlay1(
        spell = spell,
        previous = { /*TODO*/ },
        first = false,
        next = { /*TODO*/ },
        last = false,
        description = "This is a test\n I hope this here will work",
        userChoise = {
            UserDropOnly1(
                dropdown = levels,
                dropName = "Levels",
                dropChange = {
                    spell.level = it.toInt()
                    println("Level set to ${spell.level}")
                }
            )
        }
    )



// Test with both inputfield and dropdown menu
    /*
        CreateSpellOverlay1(
            spell = spell,
            previous = { /*TODO*/ },
            first = false,
            next = { /*TODO*/ },
            last = false,
            description = "This is a test\n I hope this here will work",
            userChoise = {
                UsersInputNDrop1(
                    input = spell.name,
                    inputChange = {
                        spell.name = it
                        println("Name set to ${spell.name}")
                    },
                    singleLineInput = true,
                    dropdown = levels,
                    dropName = "Levels",
                    dropChange = {
                        spell.level = it.toInt()
                        println("Level set to ${spell.level}")
                    }
                )
            }
        )
        */


}
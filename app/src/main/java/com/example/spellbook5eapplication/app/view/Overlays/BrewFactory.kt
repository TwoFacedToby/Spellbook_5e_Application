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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
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
fun CreateSpellOverlay(
    spell: Spell,
    previous: () -> Unit,
    next: () -> Unit,
    input: String,
    inputChange: (String) -> Unit,
    singleLineInput: Boolean,
    dropdown: List<String>,
    dropName: String,
    dropChange: (String) -> Unit

){
    //Utilities
    var showDialog = false

    //

    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Top navigation buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
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
            }

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
                "Next", modifier = Modifier, color = ButtonDefaults.buttonColors(
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

        UsersInputWays(input = input, inputChange, singleLineInput, dropdown, dropName, dropChange)

        // End of ways for users to give input

    }


}

@Composable
fun UsersInputWays(
    input: String,
    inputChange: (String) -> Unit,
    singleLineInput: Boolean,
    dropdown: List<String>,
    dropName: String,
    dropChange: (String) -> Unit
    ) {

    var expand by remember { mutableStateOf(false) }
    var selectedDropdownItem by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        // If input is present will a inputbar be made for the user
        if (input != null) {


            UserInputField(
                label = "name",
                //Should connect with name
                onInputChanged = { input ->
                    run { inputChange(input) }

                },
                modifier = Modifier
                    .size(width = 220.dp, height = 48.dp), singleLine = singleLineInput,
                imeAction = ImeAction.Default,
                input = input
            )

        }

        //If a dropdownbar is needed (I am in great doubt if this will work)
        if (!dropdown.isEmpty()) {


            // Button to open dropdown
            ColouredButton(
                label = "Set $dropName",
                modifier = Modifier,
                color = ButtonDefaults.buttonColors(
                    containerColor = colorResource(
                        id = R.color.selected_button
                    )
                )
            )
            { expand = true }

            if (expand) {
                dropdown.forEach { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedDropdownItem = item
                                dropChange(item)
                                expand = false
                            }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun previewCreatingPage() {

    val spell = Spell().apply {
        name = "Sample Spell"
        level = 1
    }

    val items = listOf("1", "2", "3")

    CreateSpellOverlay(
        spell = spell,
        previous = { /*TODO*/ },
        next = { /*TODO*/ },
        input = spell.name,
        inputChange = {
                input ->
            run { spell.name = input
            println("Name set to ${spell.name}") }},
        singleLineInput = true,
        dropdown = items,
        dropName = "Level",
        dropChange = {
                input ->
            run { spell.level = input.toInt()
            println("Level set to ${spell.level}") }}
    )
}
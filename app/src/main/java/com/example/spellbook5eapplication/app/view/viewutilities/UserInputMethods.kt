package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R

/**
 * Some different ways for the user to communicate with the system
 * Most (if not all) functions here are used by CreateSpellView
 */


/**
 * Dropdown bar has a button which brings the dropdown down
 * List should all be the SAME size
 * @dropdown the different displayed names in the dropdown
 * @dropName the text on the button to bring the dropdown
 * @dropchange the different functions executed when the dropdown is pressed
 */
@Composable
fun UserDropOnly(
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(157.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                dropdown.forEach { item ->
                    item {Text(
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
                    )}
                    item {Divider(
                        //color = R.color.selected_button,
                        thickness = 1.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )}
                }}}}}


/**
 * A set of buttons for the user to press
 * List should all be the SAME size
 * @buttons the different names of the buttons
 * @function the function executed by the different buttons when pressed
 * @startState is the buttons pressed down or not at the start?
 */


@Composable
fun UserButtons(
    buttons: List<String>,
    function: List<() -> Unit>,
    startState: List<Boolean>
) {
    buttons.forEachIndexed { index, label ->
        // Use the label as a key for remember
        var pressed by remember(label) { mutableStateOf(startState[index]) }

        ColouredButton(
            label = label,
            modifier = Modifier,
            color = if (pressed) {
                ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.selected_button))
            } else {
                ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.unselected_button))
            }
        ) {
            function[index].invoke()  // Invoke the function associated with the button
            pressed = !pressed // Toggle the pressed state
        }
    }

    Spacer(modifier = Modifier.height(5.dp))
}


/**
 * A list that displays what is contained in it
 * From the top can parts be removed, from the dropdown can new parts be added
 * List should all be the SAME size
 * @label text on the button to bring the dropdown
 * @possibleChoices the name of the different dropdown options
 * @function the function executed at the different dropdown options
 * @startState if any dropdown options are already in use or not
 */

@Composable
fun EditableList(
    label: String,
    possibleChoices: List<String>,
    function: (List<String>) -> Unit,
    startState: List<String>
) {
    var choosen by remember { mutableStateOf(startState) }
    var ShownChoices by remember { mutableStateOf(possibleChoices) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(5.dp))
    }

    // Currently in use items
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),

    ) {
        items(choosen) { item ->
            Card(
                backgroundColor = Color.Gray,
                modifier = Modifier
                    .padding(4.dp)
                    .wrapContentWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = item,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    IconButton(
                        onClick = {
                            // Remove from list
                            choosen = choosen.toMutableList().apply {
                                remove(item)
                            }
                            ShownChoices = ShownChoices.toMutableList().apply {
                                add(item)
                            }
                            function(choosen)
                        },
                        modifier = Modifier.size(22.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Remove from list",
                            tint = colorResource(id = R.color.spellcard_button)
                        )
                    }
                }
            }
        }
    }

    // Adding to the list
    UserDropOnly(
        dropdown = ShownChoices,
        dropName = label,
        dropChange = {
            choosen = choosen.toMutableList().apply {
                add(it)
            }
            ShownChoices = ShownChoices.toMutableList().apply {
                remove(it)
            }
            function(choosen)
        })
}



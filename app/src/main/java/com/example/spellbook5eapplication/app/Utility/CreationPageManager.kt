package com.example.spellbook5eapplication.app.Utility

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.view.Overlays.CreateNewPage
import com.example.spellbook5eapplication.app.view.Overlays.EraseOverlay
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.view.screens.BrewScreen
import com.example.spellbook5eapplication.app.view.screens.SearchScreen
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField

@Composable
fun createPageElement(spell: Spell?) {

    val navController = rememberNavController()
    var showDialog: Boolean = false


    // Start naturaly with the name
    NavHost(navController = navController, startDestination = MakePages.Name.route) {

        /**
         * Here will homebrew spells be given their name, it has a simple inputfield to put it in
         */
        composable(route = MakePages.Name.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.overlay_box_color),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {

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
                                navController.navigate(MakePages.Level.route)
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        // Field for user to place name in
                        UserInputField(
                            label = "name",
                            onInputChanged = { input ->
                                run { spell!!.name = input }
                                println("Spell name set to ${spell!!.name}")
                            },
                            modifier = Modifier
                                .size(width = 220.dp, height = 48.dp)
                                .padding(bottom = 16.dp), // Add padding to separate from the text below
                            singleLine = true,
                            imeAction = ImeAction.Default
                        )

                        // Description
                        Text(
                            text = "Name of your new spell.\nSimply put what it will be referred to as",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }


        /**
         * Here will homebrew spells be given their level, it uses a dropdown to choose the number
         */
        composable(route = MakePages.Level.route) {

            // Tells wether or not the dropdown is open
            var expand by remember { mutableStateOf(false)}


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth()
                        .background(
                            color = colorResource(id = R.color.overlay_box_color),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))

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
                                navController.navigate(MakePages.Name.route)
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
                                navController.navigate(MakePages.Level.route)
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        // Button to open dropdown
                        ColouredButton(
                            label = "Level " + spell?.level.toString(),
                            modifier = Modifier,
                            color = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = R.color.selected_button
                                )
                            ))
                        { expand = true }

                        //Description
                        Text(
                            text = "What level is the spell?.\nThe level tells how powerfull a given spell is",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        //Dropdown to select level
                        DropdownMenu(
                            expanded = expand,
                            onDismissRequest = { expand = false }, // Close dropdown when dismissed
                            modifier = Modifier.//padding(18.dp)
                            width(320.dp)
                        ) {
                            Text(
                                text = "Level 1",
                                modifier = Modifier
                                    .clickable {
                                        spell!!.level = 1
                                        expand = false
                                        println("Spell level set to ${spell!!.level}")
                                    }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 2",
                                modifier = Modifier.clickable {
                                    spell!!.level = 2
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 3",
                                modifier = Modifier.clickable {
                                    spell!!.level = 3
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 4",
                                modifier = Modifier.clickable {
                                    spell!!.level = 4
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 5",
                                modifier = Modifier.clickable {
                                    spell!!.level = 5
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 6",
                                modifier = Modifier.clickable {
                                    spell!!.level = 6
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 7",
                                modifier = Modifier.clickable {
                                    spell!!.level = 7
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 8",
                                modifier = Modifier.clickable {
                                    spell!!.level = 8
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                            Text(
                                text = "Level 9",
                                modifier = Modifier.clickable {
                                    spell!!.level = 9
                                    expand = false
                                    println("Spell level set to ${spell!!.level}")
                                }
                                    .padding(10.dp) // Add padding for larger click area
                                    .fillMaxWidth()  // Make the button fill the width of the dropdown
                                    .height(20.dp), // Specify the height of the button
                                fontSize = 16.sp, // Change the font size as needed
                                textAlign = TextAlign.Center // Optional: Align text in the center
                            )
                        }
                    }
                }
            }
        }
    }
}













// Testing
@Preview
@Composable
fun CreationPreview(){

    var spell: Spell = Spell()

     createPageElement(spell)

}
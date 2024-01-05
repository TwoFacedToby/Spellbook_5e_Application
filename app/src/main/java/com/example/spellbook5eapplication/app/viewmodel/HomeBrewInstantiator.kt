package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Utility.HomeBrewManager
import com.example.spellbook5eapplication.app.view.Overlays.CreateSpellOverlay2
import com.example.spellbook5eapplication.app.view.Overlays.EraseOverlay
import com.example.spellbook5eapplication.app.view.Overlays.UserDropOnly
import com.example.spellbook5eapplication.app.view.Overlays.UsersInputOnly
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton


//Works with BrewFactory2 to create homebrews



// Old
class HomeBrewInstantiator {



    @Composable
    fun makeNewSpellFromTheTop() {

        val spell = Spell().apply {
            homebrew = true
        }

        var first by remember { mutableStateOf(true) }
        var last by remember { mutableStateOf(false) }
        var show by remember { mutableStateOf(BrewParts.NAME) }
        var showDialog = false

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

                        if (!first) {

                            ColouredButton(
                                label = "Previous",
                                modifier = Modifier,
                                color = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(
                                        id = R.color.selected_button
                                    )
                                )
                            ) {
                                show = show.previousBrewPart()!!
                            }
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

                        if (!last) {
                        // Button to move on
                        ColouredButton(
                            "next", modifier = Modifier, color = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = R.color.green_button
                                )
                            )
                        ) {
                            show = show.nextBrewPart()!!
                        }
                        }
                        else{
                            ColouredButton(
                                "Finish", modifier = Modifier, color = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(
                                        id = R.color.green_button
                                    )
                                )
                            ) {
                                //Save the spell on the device here
                            }
                        }
                    }

                    //End of top navigation

                    Spacer(modifier = Modifier.height(10.dp))

                    // The part dependent of what is being edited/created for the spell
                    ShowBrewPart(show, spell)
                }
            }
        }
    }
//Old

    fun CreateSpell(): @Composable () -> Unit
     {
        val spell = Spell().apply {
            homebrew = true
        }

        return { Naming(spell = spell) }

    }

    fun EditSpell(spell: Spell) : @Composable () -> Unit{

        return { Naming(spell = spell) }

    }



    @Composable
    private fun Naming(spell: Spell) {
            CreateSpellOverlay2(
                spell = spell,
                //previous = { /*TODO*/ },
                //first = true,
                //next = { Description(spell) },
                //last = false,
                description = "Give your spell a name\n Simply put what your spell will be reffered by",
                userChoise = {
                    UsersInputOnly(
                        input = spell.name,
                        inputChange = {
                            spell.name = it
                            println("Name set to ${spell.name}")
                        },
                        singleLineInput = true,
                    )
                })

    }


    private fun Level(spell: Spell): @Composable () -> Unit {
        val levels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

        return {
            CreateSpellOverlay2(
                spell = spell,
                //previous = { Naming(spell) },
                //first = false,
                //next = { Description(spell) },
                //last = false,
                description = "What level is the spell\n Level explains how powerfull a spell is, higher level more powerfull",
                userChoise = {
                    UserDropOnly(
                        dropdown = levels,
                        dropName = "Levels",
                        dropChange = {
                            spell.level = it.toInt()
                            println("Level set to ${spell.level}")
                        }
                    )
                })
        }
    }

    private fun Description(spell: Spell): @Composable () -> Unit {
        var manager: HomeBrewManager =
            HomeBrewManager()

        return {
            CreateSpellOverlay2(
                spell = spell,
                //previous = { Level(spell) },
                //first = false,
                //next = { println("We have now created the spell: ${spell.toString()}")
                //     },
                //last = true,
                description = "Explain what the spell does\n What effect does it have, how is it used, anything can be put here",
                userChoise = {
                    UsersInputOnly(
                        input = spell.desc.toString(),
                        inputChange = {
                            spell.desc = listOf(it)
                            println("Name set to ${spell.desc}")
                        },
                        singleLineInput = false,
                    )
                })
        }
    }





    // ENUM
    enum class BrewParts {
        NAME,
        DESCRIPTION,
        LEVEL,
        HIGHLEVEL,
        COMPONENTS,
        MATERIAL,
        RITUAL,
        CONCENTRATION,
        RANGE,
        DURATION,
        CASTTIME,
        CLASSES,
        ATTACKTYPE,
        DAMAGE,
        DC,
        AOE;


        fun nextBrewPart(): BrewParts? = when (this) {
            NAME -> DESCRIPTION
            DESCRIPTION -> LEVEL
            LEVEL -> HIGHLEVEL
            HIGHLEVEL -> COMPONENTS
            COMPONENTS -> MATERIAL
            MATERIAL -> RITUAL
            RITUAL -> CONCENTRATION
            CONCENTRATION -> RANGE
            RANGE -> DURATION
            DURATION -> CASTTIME
            CASTTIME -> CLASSES
            CLASSES -> ATTACKTYPE
            ATTACKTYPE -> DAMAGE
            DAMAGE -> DC
            DC -> AOE
            AOE -> null
        }

        fun previousBrewPart(): BrewParts? = when (this) {
            NAME -> null
            DESCRIPTION -> NAME
            LEVEL -> DESCRIPTION
            HIGHLEVEL -> LEVEL
            COMPONENTS -> HIGHLEVEL
            MATERIAL -> COMPONENTS
            RITUAL -> MATERIAL
            CONCENTRATION -> RITUAL
            RANGE -> CONCENTRATION
            DURATION -> RANGE
            CASTTIME -> DURATION
            CLASSES -> CASTTIME
            ATTACKTYPE -> CLASSES
            DAMAGE -> ATTACKTYPE
            DC -> DAMAGE
            AOE -> DC
        }
    }

    // Use the spot in enum your in to get the right composable for editing/creating the spell
    @Composable
    fun ShowBrewPart(part: BrewParts, spell: Spell) {
        when (part) {
            BrewParts.NAME -> Naming(spell)
            BrewParts.DESCRIPTION -> Description(spell)
            BrewParts.LEVEL -> Level(spell)
            else -> {Text("There was an issue!")}
        }
    }

}


@Preview
@Composable
fun testMan(){
    val home: HomeBrewInstantiator = HomeBrewInstantiator()

    home.run {
        CreateSpell()
    }
}
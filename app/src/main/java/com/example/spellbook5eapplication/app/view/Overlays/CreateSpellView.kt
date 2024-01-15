package com.example.spellbook5eapplication.app.view.Overlays

import SpellQueryViewModel
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
//import com.example.spellbook5eapplication.app.view.Overlays.UserInputMethods
import com.example.spellbook5eapplication.app.view.viewutilities.ColouredButton
import com.example.spellbook5eapplication.app.view.viewutilities.EditableList
import com.example.spellbook5eapplication.app.view.viewutilities.UserButtons
import com.example.spellbook5eapplication.app.view.viewutilities.UserDropOnly
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType


//Works with BrewFactory2 to create homebrews



// Old
class HomeBrewInstantiator {


    /**
     * Create the overlay for creating a new Spell using a CreateSpellViewModel to do the technical things!
     * This bad boy has a few private it uses to create a guided tour
     * through the different parts of a spell
     */
    @Composable
    fun makeNewSpellFromTheTop(createViewModel: CreateSpellViewModel) {

        val spellQueryViewModel: SpellQueryViewModel = viewModel()

        var show by remember { mutableStateOf(BrewParts.NAME) }
        var changeShow by remember { mutableStateOf(BrewParts.DESCRIPTION) }
        var showDialog = false
        var alpha by remember { mutableStateOf(1f) }

        val animatedAlpha by animateFloatAsState(
            targetValue = alpha,
            animationSpec = tween(durationMillis = 250), label = ""
        )

        LaunchedEffect(animatedAlpha) {
            if (animatedAlpha == 0f) {
                show = changeShow
                alpha = 1f // Start fade in
            }
        }

        // This box stops user from interacting with other parts of the app
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { /* consume tap events */ }
                }
                .background(Color.Black.copy(alpha = 0.5f)), // Semi-transparent background
            contentAlignment = Alignment.Center
        ) {

            Box(
                contentAlignment = Alignment.Center, // Center content in the Box
                modifier = Modifier.fillMaxSize()    // Make Box fill the entire available space
            ) {

                Column(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 15.dp, end = 15.dp),//.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //Testing visual
                    Box(
                        modifier = Modifier
                            .height(600.dp)
                            .fillMaxWidth()
                            .background(
                                color = colorResource(id = R.color.overlay_box_color),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.TopCenter
                    ) {

                        Column(
                            modifier = Modifier
                                .alpha(animatedAlpha)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {

                            // Name of spell
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = show.displayedBrewPart(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Top navigation buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                if (show != BrewParts.NAME) {

                                    ColouredButton(
                                        label = "Back",
                                        modifier = Modifier
                                            .height(38.dp)
                                            .width(100.dp),
                                        color = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(
                                                id = R.color.selected_button
                                            )
                                        )
                                    ) {

                                        changeShow = show.previousBrewPart()!!
                                        alpha = 0f
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                ColouredButton(
                                    "Cancel", modifier = Modifier
                                        .height(38.dp) // Sets the height of the button
                                        .width(100.dp), color = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.red_button
                                        )
                                    )
                                ) {
                                    GlobalOverlayState.showOverlay(
                                        OverlayType.ERASE_PROMPT
                                    )
                                }


                                Spacer(modifier = Modifier.width(10.dp))

                                if (show != BrewParts.DC) {
                                    // Button to move on
                                    ColouredButton(
                                        "Next", modifier = Modifier
                                            .height(38.dp) // Sets the height of the button
                                            .width(100.dp), color = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(
                                                id = R.color.green_button
                                            )
                                        )
                                    ) {
                                        changeShow = show.nextBrewPart()!!
                                        alpha = 0f // Start fade out
                                    }
                                } else {
                                    ColouredButton(
                                        "Finish", modifier = Modifier
                                            .height(38.dp) // Sets the height of the button
                                            .width(100.dp), color = ButtonDefaults.buttonColors(
                                            containerColor = colorResource(
                                                id = R.color.green_button
                                            )
                                        )
                                    ) {
                                        //Save the spell on the device here
                                        createViewModel.saveSpell()
                                        spellQueryViewModel.loadHomebrewList()
                                        GlobalOverlayState.dismissOverlay()
                                    }
                                }
                            }

                            //End of top navigation

                            Spacer(modifier = Modifier.height(10.dp))

                            // The part dependent of what is being edited/created for the spell
                            ShowBrewPart(show, createViewModel)

                            // Testing the possibility of jumping in parts
                            NavigateBrewParts(
                                brewParts = BrewParts.values().toList(),
                                dropName = "Parts of spell to edit",
                                brewChange = {
                                    changeShow = it
                                    alpha = 0f
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    /**
     * Create the overlay for creating a new Spell using a CreateSpellViewModel to do the technical things!
     * This bad boy has a few private it uses to create a guided tour
     * through the different parts of a spell
     */
    @Composable
    fun EditSpell(createViewModel: CreateSpellViewModel) {

        val spellQueryViewModel: SpellQueryViewModel = viewModel()
        val oldIndex = createViewModel.spell.index

        var show by remember { mutableStateOf(BrewParts.NAME) }
        var changeShow by remember { mutableStateOf(BrewParts.DESCRIPTION) }
        var showDialog = false
        var alpha by remember { mutableStateOf(1f) }

        val animatedAlpha by animateFloatAsState(
            targetValue = alpha,
            animationSpec = tween(durationMillis = 250), label = ""
        )

        LaunchedEffect(animatedAlpha) {
            if (animatedAlpha == 0f) {
                show = changeShow
                alpha = 1f // Start fade in
            }
        }

        // This box stops user from interacting with other parts of the app
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { /* consume tap events */ }
                }
                .background(Color.Black.copy(alpha = 0.5f)), // Semi-transparent background
            contentAlignment = Alignment.Center
        ) {

            Box(
                contentAlignment = Alignment.Center, // Center content in the Box
                modifier = Modifier.fillMaxSize()    // Make Box fill the entire available space
            ) {

                Column(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 15.dp, end = 15.dp),//.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //Testing visual
                    Box(
                        modifier = Modifier
                            .height(600.dp)
                            .fillMaxWidth()
                            .background(
                                color = colorResource(id = R.color.overlay_box_color),
                                shape = RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.TopCenter
                    ) {

                        Column(
                            modifier = Modifier
                                .alpha(animatedAlpha)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {

                            // Name of spell
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = show.displayedBrewPart(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Top navigation buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {


                                Spacer(modifier = Modifier.width(10.dp))

                                // Button to move on
                                ColouredButton(
                                    "Done", modifier = Modifier
                                        .height(38.dp) // Sets the height of the button
                                        .width(100.dp), color = ButtonDefaults.buttonColors(
                                        containerColor = colorResource(
                                            id = R.color.green_button
                                        )
                                    )
                                ) {
                                    createViewModel.replaceSpell(oldIndex!!)
                                    spellQueryViewModel.loadHomebrewList()
                                    GlobalOverlayState.dismissOverlay()
                                }
                            }

                            //End of top navigation

                            Spacer(modifier = Modifier.height(10.dp))

                            // The part dependent of what is being edited/created for the spell
                            ShowBrewPart(show, createViewModel)

                            // Testing the possibility of jumping in parts
                            NavigateBrewParts(
                                brewParts = BrewParts.values().toList(),
                                dropName = "Parts of spell to edit",
                                brewChange = {
                                    changeShow = it
                                    alpha = 0f
                                }
                            )
                        }
                    }
                }
            }
        }
    }



    /**
     * Here is where the UI for each spell part is encapsulated
     * Each has a special view and a descriptive guide
     */
    @Composable
    private fun CreateBrewPartDependentRegion(
        description: String,
        userChoise: @Composable () -> Unit
    ) {
            userChoise()

            // End of ways for users to give input

            Spacer(Modifier.height(10.dp))


            Text(
                text = description,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }

    /**
     * A differernt kind of dropdown specificaly made for bottom of create spell overlay
     */

    @Composable
    fun NavigateBrewParts(
        brewParts: List<BrewParts>,
        dropName: String,
        brewChange: (BrewParts) -> Unit
    ) {

        var expand by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {


            // Button to open dropdown
            Text(
                text = dropName,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expand = !expand
                    })
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
                    brewParts.forEach { item ->
                        item {
                            Text(
                                text = item.displayedBrewPart(),
                                color = colorResource(id = R.color.white),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        brewChange(item)
                                        expand = false
                                    }
                                    .padding(vertical = 5.dp) // Size of each dropdown item
                            )
                        }
                        item {
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
    }

    /**
     * Now the next many private function will be to create the user input for different
     * spell parts such as name, level, classes, aoe and more (more is not a spell part ;P)
     */


    @Composable
    private fun Naming(viewModel: CreateSpellViewModel) {

        CreateBrewPartDependentRegion(
            description = "Give your spell a name\nSimply put what your spell will be reffered by",
            userChoise = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserInputField(
                        label = "Name",
                        onInputChanged = {
                            viewModel.updateName(it)
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                        singleLine = true,
                        imeAction = ImeAction.Default,
                        initialInput = viewModel.spell.name.toString()
                    )
                }
            })

    }


    @Composable
    private fun Level(viewModel: CreateSpellViewModel) {

        val levels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        var label = viewModel.spell.level.toString()

        if (label == "0") {
            label = "Level"
        }

        CreateBrewPartDependentRegion(
            description = "What level is the spell\nLevel explains how powerfull a spell is, higher level is more powerfull",
            userChoise = {
                UserDropOnly(
                    dropdown = levels,
                    dropName = label,
                    dropChange = {
                        viewModel.updateLevel(it.toInt())
                    }
                )
            })
    }

    @Composable
    private fun Description(viewModel: CreateSpellViewModel) {

        var showVal = viewModel.spell.atHigherLevel.toString()

        if (showVal.length >= 2) {
            showVal = showVal.substring(1, showVal.length - 1)
        }

        CreateBrewPartDependentRegion(
            description = "Explain what the spell does\nWhat effect does it have, how is it used, anything can be put here",
            userChoise = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserInputField(
                        label = "Description",
                        //Should connect with name
                        onInputChanged = {
                            viewModel.updateDescription(listOf(it))
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 240.dp),
                        singleLine = false,
                        imeAction = ImeAction.Default,
                        initialInput = showVal
                        //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                    )
                }
            })

    }

    @Composable
    private fun HigherLevel(viewModel: CreateSpellViewModel) {

        var showVal = viewModel.spell.atHigherLevel.toString()

        if (showVal.length >= 2) {
            showVal = showVal.substring(1, showVal.length - 1)
        }

        CreateBrewPartDependentRegion(
            description = "Optional: Tell how the spell function at higher levels",
            userChoise = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {


                    UserInputField(
                        label = "At higher level quirks",
                        //Should connect with name
                        onInputChanged = {
                            viewModel.updateHigherLevel(listOf(it))
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 240.dp),
                        singleLine = false,
                        imeAction = ImeAction.Default,
                        initialInput = showVal
                        //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                    )
                }
            })

    }


    @Composable
    private fun Components(viewModel: CreateSpellViewModel) {

        val components = listOf("Verbal", "Semantic", "Material")

        val newList = remember {
            mutableStateListOf("", "", "")
        }

        //Add what might be saved on spell to the list being worked on
        var i: Int = 0

        while (i < viewModel.spell.components!!.size) {
            newList.set(i, viewModel.spell.components!!.get(i))
            i++
        }

        // Is the Material in use if so add it
        var material by remember { mutableStateOf(viewModel.spell.components!!.contains("Material")) }

        CreateBrewPartDependentRegion(//spell = spell,
            description = "Verbal is when a spell must be spoken"
                    + "\nSemantic is when a spell need to be gestured"
                    + "\nMaterial is regions required to cast a spell choosing this option will let you write what "
                    + "ingredients is required to cast the spell",
            userChoise =
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserButtons(buttons = components,
                        function = listOf(
                            // Verbal
                            {
                                if (viewModel.spell.components!!.contains("V")) {
                                    newList.set(0, "")
                                    viewModel.updateComponents(newList)
                                } else {
                                    newList.set(0, "V")
                                    viewModel.updateComponents(newList)
                                }
                            },
                            // Semantic
                            {
                                if (viewModel.spell.components!!.contains("S")) {
                                    newList.set(1, "")
                                    viewModel.updateComponents(newList)
                                } else {
                                    newList.set(1, "S")
                                    viewModel.updateComponents(newList)
                                }
                            },
                            //Material
                            {
                                if (viewModel.spell.components!!.contains("M")) {
                                    newList.set(2, "")
                                    viewModel.updateComponents(newList)
                                    material = false
                                    // Removes materials from the spell
                                    viewModel.updateMaterial("")
                                } else {
                                    newList.set(2, "M")
                                    viewModel.updateComponents(newList)
                                    material = true
                                }
                            }
                        ),
                        startState = listOf(
                            viewModel.spell.components!!.contains("V"),
                            viewModel.spell.components!!.contains("S"),
                            viewModel.spell.components!!.contains("M")
                        ))
                }

                Spacer(modifier = Modifier.height(5.dp))

                if (material) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        UserInputField(
                            label = "Materials",
                            //Should connect with name
                            onInputChanged = {
                                viewModel.updateMaterial(it)
                            },
                            modifier = Modifier
                                .size(width = 220.dp, height = 48.dp),
                            singleLine = true,
                            imeAction = ImeAction.Default,
                            initialInput = viewModel.spell.materials.toString()
                            //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                        )

                    }
                }

            }
        )

    }


    @Composable
    private fun RitualAndConcentration(viewModel: CreateSpellViewModel) {
        CreateBrewPartDependentRegion(
            description = "Does the spell requiere concentration? Is it a ritual?" +
                    "\nConcentration spells require concentration to maintain their effects."
                    + "\nRituals take longer to cast but don't consume a spell slot.",
            userChoise = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserButtons(buttons = listOf("Concentration", "Ritual"),
                        function = listOf(
                            {
                                viewModel.updateConcentration(!viewModel.spell.concentration!!)
                            },
                            {
                                viewModel.updateRitual(!viewModel.spell.ritual!!)
                            }
                        ),
                        startState = listOf(
                            viewModel.spell.concentration!!,
                            viewModel.spell.ritual!!
                        )
                    )
                }
            })
    }


    @Composable
    private fun Range(viewModel: CreateSpellViewModel) {

        val ranges = listOf("Specific", "Self", "Touch", "Sight", "Unlimited")
        var specific by remember { mutableStateOf(false) }
        var label = viewModel.spell.range.toString()

        if (label == "") {
            label = "Range"
        }

        CreateBrewPartDependentRegion(
            description = "How far can the spell reach?" +
                    "\nIf specific will you be asked to write down the range such as \"2 meters\" or \"1 feet\"",
            userChoise = {
                UserDropOnly(dropdown = ranges,
                    dropName = label,
                    dropChange = {
                        viewModel.updateRange(it)
                        specific = it == "Specific"
                    })

                Spacer(modifier = Modifier.height(5.dp))

                if (specific) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        UserInputField(
                            label = "Specific range",
                            //Should connect with name
                            onInputChanged = {
                                viewModel.updateRange(it)
                            },
                            modifier = Modifier
                                .size(width = 220.dp, height = 48.dp),
                            singleLine = true,
                            imeAction = ImeAction.Default,
                            initialInput = viewModel.spell.range.toString()
                            //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                        )
                    }
                }

            }
        )
    }

    @Composable
    private fun Duration(viewModel: CreateSpellViewModel) {

        val durations = listOf(
            "Specific",
            "Instantaneous",
            "Concentration",
            "Until dispelled",
            "Until dispelled or triggered"
        )
        var specific by remember { mutableStateOf(false) }
        var label = viewModel.spell.duration.toString()

        if (label == "") {
            label = "Duration"
        }

        CreateBrewPartDependentRegion(
            description = "How long does the spell last?" +
                    "\nIf specific is choosen, give time such as \"1 day\" or \"2 rounds\" or what you seek ",
            userChoise = {


                UserDropOnly(dropdown = durations,
                    dropName = label,
                    dropChange = {
                        viewModel.updateDuration(it)
                        specific = it == "Specific"
                    })

                Spacer(modifier = Modifier.height(5.dp))

                if (specific) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {


                        UserInputField(
                            label = "Specific duration",
                            //Should connect with name
                            onInputChanged = {
                                viewModel.updateDuration(it)
                            },
                            modifier = Modifier
                                .size(width = 220.dp, height = 48.dp),
                            singleLine = true,
                            imeAction = ImeAction.Default,
                            initialInput = viewModel.spell.duration.toString()
                            //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                        )
                    }
                }


            })
    }

    @Composable
    private fun CastTime(viewModel: CreateSpellViewModel) {

        val durations = listOf(
            "Specific",
            "Instantaneous",
            "Concentration",
            "Until dispelled",
            "Until dispelled or triggered"
        )

        CreateBrewPartDependentRegion(
            description = "How long does it take to cast the spell?" +
                    "\ngive times such as \"1 minute\" or \"2 rounds\" or what you seek ",
            userChoise = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserInputField(
                        label = "Cast time",
                        //Should connect with name
                        onInputChanged = {
                            viewModel.updateCastTime(it)
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                        singleLine = true,
                        imeAction = ImeAction.Default,
                        initialInput = viewModel.spell.casting_time.toString()
                        //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                    )

                }


            })
    }


    @Composable
    private fun School(viewModel: CreateSpellViewModel) {
        val schools = listOf(
            "Abjuration",
            "Conjuration",
            "Divination",
            "Enchantment",
            "Evocation",
            "Illusion",
            "Necromancy",
            "Transmutation"
        )

        var label = viewModel.spell.school!!.name.toString()

        if (label == "") {
            label = "School"
        }

        CreateBrewPartDependentRegion(
            description = "What kind of spell is it?" +
                    "\nSpells are categorized into different schools of magic like Evocation (dealing damage) or Divination (gaining information).",
            userChoise = {


                UserDropOnly(dropdown = schools,
                    dropName = label.toString(),
                    dropChange = {
                        viewModel.updateSchool(it)
                    })
            })
    }


    @Composable
    private fun Classes(viewModel: CreateSpellViewModel) {

        val initialClassesAmount = viewModel.spell.classes!!.size
        var i: Int = 0
        // Place all the components in the list we are working with
        val mutableClasses = remember {
            mutableStateListOf<String>().apply {
                while (i < initialClassesAmount) {
                    add(viewModel.spell.classes!!.get(i).name.toString())
                    i++
                }
            }
        }

        CreateBrewPartDependentRegion(
            description = "What classes can use the spell?" +
                    "\nSome spells can only be used by certain classes",
            userChoise = {

                EditableList(
                    label = "Classes",
                    possibleChoices = listOf(
                        "Artificer",
                        "Bard",
                        "Blood hunter",
                        "Cleric",
                        "Druid",
                        "Fighter",
                        "Paladin",
                        "Ranger",
                        "Rouge",
                        "Sorcerer",
                        "Warlock",
                        "Wizard"
                    ),
                    function = {
                        viewModel.updateClass(it)
                        // Println for testing (Crashes when asking for last in empty list)
                        //println("Spell classes has last in list: ${spell.classes.last()}")
                    },
                    startState = mutableClasses
                )

            })
    }

    @Composable
    private fun AttackType(viewModel: CreateSpellViewModel) {
        CreateBrewPartDependentRegion(
            description = "Explain what type of attack the spell has" +
                    "\nsuch as fire, cold, or slashing.",
            userChoise = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserInputField(
                        label = "Attack type",
                        //Should connect with name
                        onInputChanged = {
                            viewModel.updateAttackType(it)
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                        singleLine = true,
                        imeAction = ImeAction.Default,
                        initialInput = viewModel.spell.attackType.toString()
                        //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                    )

                }
            })
    }

    @Composable
    private fun Damage(viewModel: CreateSpellViewModel) {
        CreateBrewPartDependentRegion(
            description = "Explain how much damage is inflicted by the spell",
            userChoise = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserInputField(
                        label = "Damage",
                        //Should connect with name
                        onInputChanged = {
                            viewModel.updateDamage(it)
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                        singleLine = true,
                        imeAction = ImeAction.Default,
                        initialInput = viewModel.spell.damage!!.damageType!!.name.toString()
                        //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                    )

                }
            })
    }

    @Composable
    private fun DC(viewModel: CreateSpellViewModel) {
        CreateBrewPartDependentRegion(
            description = "How difficult is the spell?" +
                    "\nDC is the number the target needs to roll on a 20-sided die (d20) to resist the spell's effects. The caster's spellcasting ability often determines this DC.",
            userChoise = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    UserInputField(
                        label = "Difficulty Class",
                        //Should connect with name
                        onInputChanged = {
                            viewModel.updateDC(it)
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                        singleLine = true,
                        imeAction = ImeAction.Default,
                        initialInput = viewModel.spell.dc.toString()
                        //input = input (In the future one could make so the input isnt "" by default, this will make editing easier)
                    )
                }
            })
    }


    /**
     * Enum here keeps track of the differetn parts a spell is build with
     * First is the part, what will be the next part, previous,
     * name to display for the part and what private function to run with the part
     */
    enum class BrewParts {
        NAME,
        DESCRIPTION,
        LEVEL,
        HIGHLEVEL,
        COMPONENTS,
        RANGE,
        CONCENTANDRITUAL,
        DURATION,
        CASTTIME,
        SCHOOL,
        CLASSES,
        ATTACKTYPE,
        DAMAGE,
        DC;


        // What spell part to go to when pressing next
        fun nextBrewPart(): BrewParts? = when (this) {
            NAME -> DESCRIPTION
            DESCRIPTION -> LEVEL
            LEVEL -> HIGHLEVEL
            HIGHLEVEL -> COMPONENTS
            COMPONENTS -> CONCENTANDRITUAL
            CONCENTANDRITUAL -> RANGE
            RANGE -> DURATION
            DURATION -> CASTTIME
            CASTTIME -> SCHOOL
            SCHOOL -> CLASSES
            CLASSES -> ATTACKTYPE
            ATTACKTYPE -> DAMAGE
            DAMAGE -> DC
            DC -> null
        }

        // What spell parts to use when going to previous
        fun previousBrewPart(): BrewParts? = when (this) {
            NAME -> null
            DESCRIPTION -> NAME
            LEVEL -> DESCRIPTION
            HIGHLEVEL -> LEVEL
            COMPONENTS -> HIGHLEVEL
            CONCENTANDRITUAL -> COMPONENTS
            RANGE -> CONCENTANDRITUAL
            DURATION -> RANGE
            CASTTIME -> DURATION
            SCHOOL -> CASTTIME
            CLASSES -> SCHOOL
            ATTACKTYPE -> CLASSES
            DAMAGE -> ATTACKTYPE
            DC -> DAMAGE
        }

        // What is the displayed name for the spell part
        fun displayedBrewPart(): String = when (this) {
            NAME -> "Name"
            DESCRIPTION -> "Description"
            LEVEL -> "Level"
            HIGHLEVEL -> "At higher level"
            COMPONENTS -> "Components"
            CONCENTANDRITUAL -> "Concentration and ritual"
            RANGE -> "Range"
            DURATION -> "Duration"
            CASTTIME -> "Cast time"
            SCHOOL -> "School"
            CLASSES -> "Classes"
            ATTACKTYPE -> "Attack type"
            DAMAGE -> "Damage"
            DC -> "Difficulty"
        }
    }

    // What function each enum should use
    @Composable
    fun ShowBrewPart(part: BrewParts, viewModel: CreateSpellViewModel) {
            when (part) {
                BrewParts.NAME -> Naming(viewModel)
                BrewParts.DESCRIPTION -> Description(viewModel)
                BrewParts.LEVEL -> Level(viewModel)
                BrewParts.HIGHLEVEL -> HigherLevel(viewModel)
                BrewParts.COMPONENTS -> Components(viewModel)
                BrewParts.RANGE -> Range(viewModel)
                BrewParts.CONCENTANDRITUAL -> RitualAndConcentration(viewModel)
                BrewParts.DURATION -> Duration(viewModel)
                BrewParts.CASTTIME -> CastTime(viewModel)
                BrewParts.SCHOOL -> School(viewModel)
                BrewParts.CLASSES -> Classes(viewModel)
                BrewParts.ATTACKTYPE -> AttackType(viewModel)
                BrewParts.DAMAGE -> Damage(viewModel)
                BrewParts.DC -> DC(viewModel)
                else -> {
                    Text("There was an issue!")
                }
            }
        }
    }

/**
 * This is preview for testing
 *
 */

@Preview
@Composable
fun testMan(){
    val home: HomeBrewInstantiator = HomeBrewInstantiator()
    val viewModel = CreateSpellViewModel()
    home.makeNewSpellFromTheTop(viewModel)


}
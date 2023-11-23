package com.example.spellbook5eapplication.app.view.Overlays

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.HomeBrewManager
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellController.saveHomeJSON
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.MakeItem
import com.example.spellbook5eapplication.app.viewmodel.mHighRange
import com.example.spellbook5eapplication.app.viewmodel.martificer
import com.example.spellbook5eapplication.app.viewmodel.mbard
import com.example.spellbook5eapplication.app.viewmodel.mcharisma
import com.example.spellbook5eapplication.app.viewmodel.mcleric
import com.example.spellbook5eapplication.app.viewmodel.mconstitution
import com.example.spellbook5eapplication.app.viewmodel.mdexterity
import com.example.spellbook5eapplication.app.viewmodel.mdruid
import com.example.spellbook5eapplication.app.viewmodel.mintelligence
import com.example.spellbook5eapplication.app.viewmodel.mlevel0
import com.example.spellbook5eapplication.app.viewmodel.mlevel1
import com.example.spellbook5eapplication.app.viewmodel.mlevel2
import com.example.spellbook5eapplication.app.viewmodel.mlevel3
import com.example.spellbook5eapplication.app.viewmodel.mlevel4
import com.example.spellbook5eapplication.app.viewmodel.mlevel5
import com.example.spellbook5eapplication.app.viewmodel.mlevel6
import com.example.spellbook5eapplication.app.viewmodel.mlevel7
import com.example.spellbook5eapplication.app.viewmodel.mlevel8
import com.example.spellbook5eapplication.app.viewmodel.mlevel9
import com.example.spellbook5eapplication.app.viewmodel.mlowRange
import com.example.spellbook5eapplication.app.viewmodel.mmatrial
import com.example.spellbook5eapplication.app.viewmodel.mmediumRange
import com.example.spellbook5eapplication.app.viewmodel.mnoConcentration
import com.example.spellbook5eapplication.app.viewmodel.mnoRange
import com.example.spellbook5eapplication.app.viewmodel.mnoRitual
import com.example.spellbook5eapplication.app.viewmodel.msemantic
import com.example.spellbook5eapplication.app.viewmodel.mstrength
import com.example.spellbook5eapplication.app.viewmodel.mverbal
import com.example.spellbook5eapplication.app.viewmodel.mwisdom
import com.example.spellbook5eapplication.app.viewmodel.myesConcentration
import com.example.spellbook5eapplication.app.viewmodel.myesRitual

@Composable
fun NewSpellOverlay(
    onDismissRequest: () -> Unit,
    onFilterSelected: (MakeItem) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("")}

    var description by remember { mutableStateOf("")}

    var range by remember { mutableStateOf("")}

    var school by remember { mutableStateOf("")}

    var duration by remember { mutableStateOf("")}

    var castTime by remember { mutableStateOf("")}

    val spelllevel = remember {
        mutableStateListOf(
            mlevel0, mlevel1, mlevel2, mlevel3, mlevel4, mlevel5, mlevel6, mlevel7, mlevel8, mlevel9
        )
    }

    val concentration = remember {
        mutableStateListOf(
            myesConcentration, mnoConcentration
        )
    }
    val ritual = remember {
        mutableStateListOf(
            myesRitual, mnoRitual
        )
    }

    val components = remember {
        mutableStateListOf(
            mverbal, msemantic, mmatrial
        )
    }


    var selectedComponents by remember { mutableStateOf(emptyList<String>()) }

    var selectedLevel by remember {mutableStateOf(0)}

    val saveReq = remember {
        mutableStateListOf(
            mstrength, mdexterity, mconstitution, mintelligence, mwisdom, mcharisma,
        )
    }

    var manager: HomeBrewManager = HomeBrewManager()


    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /*
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .clickable { onDismissRequest() },
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
        */
        Spacer(modifier = Modifier.height(20.dp))

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp))
            {
                item {Spacer(modifier = Modifier.height(5.dp))}

                // Giving the spell a name, name is just printed at this moment
                item {Text(
                    text = "Name your new spell",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                item {
                    UserInputField(
                        label = "name",
                        //Should connect with name
                        onInputChanged = {
                                input ->
                            run { name = input }

                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp), singleLine = true
                    )
                }
                item {Spacer(modifier = Modifier.height(5.dp))}

                // Chossing the level of the spell
                item {Text(
                    text = "Spell Level",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                spelllevel.chunked(5).forEach { rowLevels ->
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowLevels.forEach { level ->
                                val isSelected = (selectedLevel == (level.label.toInt()))

                                Button(
                                    modifier = Modifier.size(width = 55.dp, height = 55.dp),
                                    contentPadding = PaddingValues(1.dp),
                                    onClick = {
                                            selectedLevel = level.label.toInt()
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected) colorResource(id = R.color.selected_button)
                                        else colorResource(id = R.color.unselected_button)
                                    ),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = colorResource(id = R.color.border_color)
                                    ),
                                    shape = RoundedCornerShape(5.dp),
                                ) {
                                    Text(
                                        text = level.label,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            /*
                            rowLevels.forEach { level ->
                                CreateButton(
                                    modifier = Modifier.size(55.dp),
                                    contentPaddingValues = PaddingValues(1.dp),
                                    filter = level,
                                    onFilterSelected = { onFilterSelected(level) }
                                )
                            }
                            */
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(5.dp)) }
                item {Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Concentration",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            concentration.forEach(){ concentration ->
                                CreateButton(
                                    modifier = Modifier.size(46.dp),
                                    contentPaddingValues = PaddingValues(1.dp),
                                    filter = concentration,
                                    onFilterSelected = { onFilterSelected(concentration)}
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Ritual",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            ritual.forEach(){ ritual ->
                                CreateButton(
                                    modifier = Modifier.size(46.dp),
                                    contentPaddingValues = PaddingValues(1.dp),
                                    filter = ritual,
                                    onFilterSelected = { onFilterSelected(ritual)}
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                }
                }
                item {Spacer(modifier = Modifier.height(5.dp))}
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        Text(
                            text = "Components",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            components.forEach { component ->
                                val isSelected = selectedComponents.contains(component.label[0].toString())

                                Button(
                                    modifier = Modifier.size(width = 100.dp, height = 40.dp),
                                    contentPadding = PaddingValues(1.dp),
                                    onClick = {
                                        selectedComponents = if (isSelected) {
                                            selectedComponents - component.label[0].toString()
                                        } else {
                                            selectedComponents + component.label[0].toString()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected) colorResource(id = R.color.selected_button)
                                        else colorResource(id = R.color.unselected_button)
                                    ),
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = colorResource(id = R.color.border_color)
                                    ),
                                    shape = RoundedCornerShape(5.dp),
                                ) {
                                    Text(
                                        text = component.label,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }

                            /*
                            components.forEach() { components ->
                                var isSelected = mutableStateOf(false)
                            Button(modifier = Modifier.size(width = 100.dp, height = 40.dp),
                                contentPadding = PaddingValues(1.dp),
                                onClick = {
                                    if(!isSelected.value){
                                        comps.add(components.label[0].toString())
                                    }
                                    else{
                                        comps.remove(components.label[0].toString())
                                    }
                                    isSelected.value = !isSelected.value
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected.value) colorResource(id = R.color.selected_button)
                                    else colorResource(id = R.color.unselected_button)
                                ),
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = R.color.border_color)
                                ),
                                shape = RoundedCornerShape(5.dp),
                            ) {
                                Text(
                                    text = components.label,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            /*
                                CreateButton(
                                    modifier = Modifier.size(width = 100.dp, height = 40.dp),
                                    contentPaddingValues = PaddingValues(1.dp),
                                    filter = components,
                                    onFilterSelected = { onFilterSelected(components) }
                                )
                                */
                            }*/
                        }
                    }
                }
                item {Spacer(modifier = Modifier.height(5.dp)) }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Save Required",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        saveReq.chunked(3).forEach { rowLevels ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowLevels.forEach { level ->
                                    CreateButton(
                                        modifier = Modifier.size(width = 100.dp, height = 40.dp),
                                        filter = level,
                                        contentPaddingValues = PaddingValues(1.dp),
                                        onFilterSelected = { onFilterSelected(level) }
                                    )
                                }
                            }
                        }
                    }
                }

                item {Spacer(modifier = Modifier.height(5.dp))}

                //Range
                item {Spacer(modifier = Modifier.height(5.dp))}
                // Giving the spell a name, name is just printed at this moment
                item {Text(
                    text = "Range",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                item {
                    UserInputField(
                        label = "Self,melee or a distance",
                        //Possible should connect this to description string
                        onInputChanged = { input -> run { range = input } },
                        modifier = Modifier
                            .size(width = 300.dp, height = 48.dp), singleLine = true
                    )
                }

                item {Spacer(modifier = Modifier.height(5.dp))}

                //Duration
                item {Spacer(modifier = Modifier.height(5.dp))}
                // Giving the spell a name, name is just printed at this moment
                item {Text(
                    text = "Duration",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                item {
                    UserInputField(
                        label = "1,2 or other actions",
                        //Possible should connect this to description string
                        onInputChanged = { input -> run { duration = input } },
                        modifier = Modifier
                            .size(width = 300.dp, height = 48.dp), singleLine = true
                    )
                }

                item {Spacer(modifier = Modifier.height(5.dp))}

                //School
                item {Spacer(modifier = Modifier.height(5.dp))}
                // Giving the spell a name, name is just printed at this moment
                item {Text(
                    text = "School",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                item {
                    UserInputField(
                        label = "What school is the spell from",
                        //Possible should connect this to description string
                        onInputChanged = { input -> run { school = input } },
                        modifier = Modifier
                            .size(width = 300.dp, height = 48.dp), singleLine = true
                    )
                }

                item {Spacer(modifier = Modifier.height(5.dp))}

                //Cast time
                item {Spacer(modifier = Modifier.height(5.dp))}
                // Giving the spell a name, name is just printed at this moment
                item {Text(
                    text = "Cast time",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                item {
                    UserInputField(
                        label = "Instantaneous, 1 min or what you imagine",
                        //Possible should connect this to description string
                        onInputChanged = { input -> run { castTime = input } },
                        modifier = Modifier
                            .size(width = 300.dp, height = 48.dp), singleLine = true
                    )
                }

                item {Spacer(modifier = Modifier.height(5.dp))}
                // Giving the spell a name, name is just printed at this moment
                item {Text(
                    text = "Describe your spell!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                item {
                    UserInputField(
                        label = "",
                        //Possible should connect this to description string
                        onInputChanged = {
                                input ->
                            run { description = input }
                        },
                        modifier = Modifier
                            .size(width = 300.dp, height = 240.dp), singleLine = false
                    )
                }

                item {Spacer(modifier = Modifier.height(5.dp))}
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        ColouredButton(
                            "Create", modifier = Modifier, color = ButtonDefaults.buttonColors(
                                containerColor = colorResource(
                                    id = R.color.green_button
                                )
                            )
                        ) {
                            //val comp: List<String> = listOf("V", "", "M")
                            // Use the parameters that you want to pass to createSpellJson
                            val createdSpellJson = manager.createSpellJson(
                                name = name,
                                description = description,
                                range = range,
                                components = selectedComponents,
                                ritual = ritual[0].isSelected.value, // Assuming the first ritual option is selected or not
                                concentration = concentration[0].isSelected.value, // Assuming the first concentration option is selected or not
                                duration = duration,
                                casting_time = castTime,
                                level = selectedLevel
                            )
                            println("Spell created: $createdSpellJson")
                            onDismissRequest()
                        }

                        Spacer(modifier = Modifier.width(30.dp))

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
                            EraseOverlay(onDismissRequest = { showDialog = false }, onEraseRequest = {onDismissRequest()})
                        }
                    }

                }
                item {Spacer(modifier = Modifier.height(5.dp))}
            }
        }
    }
}

@Composable
fun CreateButton(
    modifier: Modifier,
    contentPaddingValues: PaddingValues? = null,
    filter: MakeItem,
    onFilterSelected: (MakeItem) -> Unit) {
    if(contentPaddingValues == null) {
        Button(
            modifier = modifier,
            onClick = {
                filter.isSelected.value = !filter.isSelected.value
                onFilterSelected(filter)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (filter.isSelected.value) colorResource(id = R.color.selected_button)
                else colorResource(id = R.color.unselected_button)
            ),
            border = BorderStroke(
                width = 2.dp,
                color = colorResource(id = R.color.border_color)
            ),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = filter.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    } else {
        Button(
            modifier = modifier,
            onClick = {
                filter.isSelected.value = !filter.isSelected.value
                onFilterSelected(filter)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (filter.isSelected.value) colorResource(id = R.color.selected_button)
                else colorResource(id = R.color.unselected_button)
            ),
            border = BorderStroke(
                width = 2.dp,
                color = colorResource(id = R.color.border_color)
            ),
            contentPadding = contentPaddingValues,
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = filter.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


@Preview
@Composable
fun CreateOverlayPreview(){
    NewSpellOverlay(
        onDismissRequest = { println("Dismiss button clicked") },
        onFilterSelected = { println("filter selected") })
}

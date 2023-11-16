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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.viewmodel.FilterItem
import com.example.spellbook5eapplication.app.viewmodel.artificer
import com.example.spellbook5eapplication.app.viewmodel.bard
import com.example.spellbook5eapplication.app.viewmodel.charisma
import com.example.spellbook5eapplication.app.viewmodel.cleric
import com.example.spellbook5eapplication.app.viewmodel.constitution
import com.example.spellbook5eapplication.app.viewmodel.dexterity
import com.example.spellbook5eapplication.app.viewmodel.druid
import com.example.spellbook5eapplication.app.viewmodel.intelligence
import com.example.spellbook5eapplication.app.viewmodel.level0
import com.example.spellbook5eapplication.app.viewmodel.level1
import com.example.spellbook5eapplication.app.viewmodel.level2
import com.example.spellbook5eapplication.app.viewmodel.level3
import com.example.spellbook5eapplication.app.viewmodel.level4
import com.example.spellbook5eapplication.app.viewmodel.level5
import com.example.spellbook5eapplication.app.viewmodel.level6
import com.example.spellbook5eapplication.app.viewmodel.level7
import com.example.spellbook5eapplication.app.viewmodel.level8
import com.example.spellbook5eapplication.app.viewmodel.level9
import com.example.spellbook5eapplication.app.viewmodel.matrial
import com.example.spellbook5eapplication.app.viewmodel.noConcentration
import com.example.spellbook5eapplication.app.viewmodel.noRitual
import com.example.spellbook5eapplication.app.viewmodel.semantic
import com.example.spellbook5eapplication.app.viewmodel.strength
import com.example.spellbook5eapplication.app.viewmodel.verbal
import com.example.spellbook5eapplication.app.viewmodel.wisdom
import com.example.spellbook5eapplication.app.viewmodel.yesConcentration
import com.example.spellbook5eapplication.app.viewmodel.yesRitual


@Composable
fun FiltersOverlay(
    onDismissRequest: () -> Unit,
    onFilterSelected: (FilterItem) -> Unit,
    applyFilter:(Filter) -> Unit
) {

    val spelllevel = remember {
        mutableStateListOf(
            level0, level1, level2, level3, level4, level5, level6, level7, level8, level9
        )
    }

    val concentration = remember {
        mutableStateListOf(
            yesConcentration, noConcentration
        )
    }
    val ritual = remember {
        mutableStateListOf(
            yesRitual, noRitual
        )
    }

    val components = remember {
        mutableStateListOf(
            verbal, semantic, matrial
        )
    }

    val saveReq = remember {
        mutableStateListOf(
            strength, dexterity, constitution, intelligence, wisdom, charisma,
        )
    }
    val classes = remember {
        mutableStateListOf(
            artificer, bard, cleric, druid,

            )
    }

    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .clickable { onDismissRequest() },
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
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
                item {Button(
                    onClick = {
                        val filter = Filter()
                        for(index in 0 until spelllevel.size){
                            if(spelllevel[index].isSelected.value) filter.addLevel(index) else filter.removeLevel(index)
                        }
                        for(index in 0 until concentration.size){
                            when(index){
                                0 ->  if(concentration[index].isSelected.value) filter.addConcentration(true)
                                1 ->  if(concentration[index].isSelected.value) filter.addConcentration(false)

                            }
                        }
                        for(index in 0 until ritual.size){
                            when(index){
                                0 ->  if(ritual[index].isSelected.value) filter.addRitual(true)
                                1 ->  if(ritual[index].isSelected.value) filter.addRitual(false)

                            }
                        }
                        for(index in 0 until components.size){
                            when(index){
                                0 ->  if(components[index].isSelected.value) filter.addComponent(Filter.Component.VERBAL)
                                1 ->  if(components[index].isSelected.value) filter.addComponent(Filter.Component.SOMATIC)
                                2 ->  if(components[index].isSelected.value) filter.addComponent(Filter.Component.MATERIAL)
                           }
                        }
                        /*for(index in 0 until saveReq.size){ Not a filter yet
                            when(index){
                                0 ->  if(saveReq[index].isSelected.value) filter.
                            }
                        }*/
                        for(index in 0 until classes.size){
                            when(index){
                                0 ->  if(classes[index].isSelected.value) filter.addClass(Filter.Classes.ARTIFICER)
                                1 ->  if(classes[index].isSelected.value) filter.addClass(Filter.Classes.BARD)
                                2 ->  if(classes[index].isSelected.value) filter.addClass(Filter.Classes.CLERIC)
                                3 ->  if(classes[index].isSelected.value) filter.addClass(Filter.Classes.DRUID)
                            }
                        }

                        applyFilter(filter)

                    },
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(text = "Apply Filters",
                        color = colorResource(id = R.color.white)
                        )
                }}
                item {Spacer(modifier = Modifier.height(5.dp))}
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
                                    FilterButton(
                                        modifier = Modifier.size(55.dp),
                                        contentPaddingValues = PaddingValues(1.dp),
                                        filter = level,
                                        onFilterSelected = { onFilterSelected(level) }
                                    )
                                }
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
                                FilterButton(
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
                                FilterButton(
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
                            components.forEach() { components ->
                                FilterButton(
                                    modifier = Modifier.size(width = 100.dp, height = 40.dp),
                                    contentPaddingValues = PaddingValues(1.dp),
                                    filter = components,
                                    onFilterSelected = { onFilterSelected(components) }
                                )
                            }
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
                                    FilterButton(
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
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Spell list",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        classes.chunked(2).forEach { rowLevels ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowLevels.forEach { level ->
                                    FilterButton(
                                        modifier = Modifier.size(width = 145.dp, height = 40.dp),
                                        filter = level,
                                        onFilterSelected = { onFilterSelected(level) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    modifier: Modifier,
    contentPaddingValues: PaddingValues? = null,
    filter: FilterItem,
    onFilterSelected: (FilterItem) -> Unit) {
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

/*
@Preview
@Composable
fun FiltersOverlayPreview(){
    FiltersOverlay(
        onDismissRequest = { println("Dismiss button clicked") },
        onFilterSelected = { println("filter selected") })

}
*/
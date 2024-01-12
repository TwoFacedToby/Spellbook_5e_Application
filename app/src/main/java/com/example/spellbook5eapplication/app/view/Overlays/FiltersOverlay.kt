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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.view.utilities.ColouredButton
import com.example.spellbook5eapplication.app.viewmodel.FilterItem
import com.example.spellbook5eapplication.app.viewmodel.FilterViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState


@Composable
fun FiltersOverlay(
) {
    val spellLevel = remember { mutableStateListOf(*FilterItem.spellLevels.toTypedArray()) }
    val components = remember { mutableStateListOf(*FilterItem.components.toTypedArray()) }
    val saveReq = remember { mutableStateListOf(*FilterItem.saveReq.toTypedArray()) }
    val classes = remember { mutableStateListOf(*FilterItem.classes.toTypedArray()) }
    val concentration = remember { mutableStateListOf(*FilterItem.isConcentration.toTypedArray()) }
    val ritual = remember { mutableStateListOf(*FilterItem.isRitual.toTypedArray()) }

    val filterViewModel: FilterViewModel = viewModel()

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
                .clip(shape = RoundedCornerShape(5.dp)),
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row{
            ColouredButton(label = "Apply",
                modifier = Modifier.weight(1F),
                color = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_button)),
                onClick = { filterViewModel.applyFilters(
                    spellLevel,
                    components,
                    saveReq,
                    classes,
                    concentration,
                    ritual
                )
                    GlobalOverlayState.dismissOverlay()
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            ColouredButton(label = "Reset",
                modifier = Modifier.weight(1F),
                color = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.red_button)),
                onClick = {
                    onResetAllFiltersClicked(
                        spellLevel,
                        components,
                        saveReq,
                        classes,
                        concentration,
                        ritual
                    )
                    filterViewModel.resetCurrentFilter()
                    GlobalOverlayState.dismissOverlay()
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                item {
                    Text(
                        text = "Spell Level",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                spellLevel.chunked(5).forEach { rowLevels ->
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            rowLevels.forEach { level ->
                                FilterButton(
                                    modifier = Modifier.size(55.dp),
                                    contentPaddingValues = PaddingValues(1.dp),
                                    filter = level
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(5.dp)) }
                item {
                    Row(
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
                                concentration.forEach() { concentration ->
                                    FilterButton(
                                        modifier = Modifier.size(46.dp),
                                        contentPaddingValues = PaddingValues(1.dp),
                                        filter = concentration,
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
                                ritual.forEach() { ritual ->
                                    FilterButton(
                                        modifier = Modifier.size(46.dp),
                                        contentPaddingValues = PaddingValues(1.dp),
                                        filter = ritual,
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                }
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(5.dp)) }
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
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(5.dp)) }
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
                                    )
                                }
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(5.dp)) }
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
) {
    if (contentPaddingValues == null) {
        Button(
            modifier = modifier,
            onClick = {
                filter.isSelected.value = !filter.isSelected.value
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

fun onResetAllFiltersClicked(
    spellLevel: List<FilterItem>,
    components: List<FilterItem>,
    saveReq: List<FilterItem>,
    classes: List<FilterItem>,
    concentration: List<FilterItem>,
    ritual: List<FilterItem>,
){
    spellLevel.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    components.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    saveReq.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    classes.forEach { filterItem ->
        filterItem.isSelected.value = false
    }
    concentration.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    ritual.forEach { filterItem ->
        filterItem.isSelected.value = false
    }
}

/*fun processList(items: List<FilterItem>, updateFilter: (Filter, String) -> Unit, filter: Filter, asBoolean: Boolean = false) {
    items.forEach { item ->
        if (item.isSelected.value) {
            if (asBoolean) {
                val booleanValue = item.label.equals("Yes", ignoreCase = true)
                updateFilter(filter, booleanValue.toString())
            } else {
                updateFilter(filter, item.label)
            }
        }
    }
}

fun onApplyFiltersClicked(
    spellLevel: List<FilterItem>,
    components: List<FilterItem>,
    saveReq: List<FilterItem>,
    classes: List<FilterItem>,
    concentration: List<FilterItem>,
    ritual: List<FilterItem>,
    currentfilter: Filter,
    createNewFilter: () -> Filter,
    updateFilterState: (Filter) -> Unit,
) {
    val newFilter = createNewFilter()

    if(currentfilter.getSpellName() != ""){
        newFilter.setSpellName(currentfilter.getSpellName())
    }

    val updateSpellLevel: (Filter, String) -> Unit = { filter, value ->
        val level = value.toInt()
        filter.addLevel(level)
    }

    val updateComponents: (Filter, String) -> Unit = { filter, value ->
        when (value) {
            "Verbal" -> filter.addComponent(Filter.Component.VERBAL)
            "Semantic" -> filter.addComponent(Filter.Component.SOMATIC)
            "Material" -> filter.addComponent(Filter.Component.MATERIAL)
        }
    }

    val updateSaveReq: (Filter, String) -> Unit = { filter, value ->
        when (value) {
            "Strength" -> filter.addSaveReq(Filter.SaveReq.STRENGTH)
            "Dexterity" -> filter.addSaveReq(Filter.SaveReq.DEXTERITY)
            "Constitution" -> filter.addSaveReq(Filter.SaveReq.CONSTITUTION)
            "Wisdom" -> filter.addSaveReq(Filter.SaveReq.WISDOM)
            "Intelligence" -> filter.addSaveReq(Filter.SaveReq.INTELLIGENCE)
            "Charisma" -> filter.addSaveReq(Filter.SaveReq.CHARISMA)
        }
    }
    val updateClasses: (Filter, String) -> Unit = { filter, value ->
        when (value) {
            "Artificer" -> filter.addClass(Filter.Classes.ARTIFICER)
            "Barbarian" -> filter.addClass(Filter.Classes.BARBARIAN)
            "Bard" -> filter.addClass(Filter.Classes.BARD)
            "Cleric" -> filter.addClass(Filter.Classes.CLERIC)
            "Druid" -> filter.addClass(Filter.Classes.DRUID)
            "Fighter" -> filter.addClass(Filter.Classes.FIGHTER)
            "Monk" -> filter.addClass(Filter.Classes.MONK)
            "Paladin" -> filter.addClass(Filter.Classes.PALADIN)
            "Ranger" -> filter.addClass(Filter.Classes.RANGER)
            "Rogue" -> filter.addClass(Filter.Classes.ROGUE)
            "Sorcerer" -> filter.addClass(Filter.Classes.SORCERER)
            "Warlock" -> filter.addClass(Filter.Classes.WARLOCK)
            "Wizard" -> filter.addClass(Filter.Classes.WIZARD)
        }
    }
    val updateConcentration: (Filter, String) -> Unit = { filter, value ->
        filter.addConcentration(value.toBoolean())
    }
    val updateRitual: (Filter, String) -> Unit = { filter, value ->
        filter.addRitual(value.toBoolean())
    }

    processList(spellLevel, updateSpellLevel, newFilter)
    processList(components, updateComponents, newFilter)
    processList(saveReq, updateSaveReq, newFilter)
    processList(classes, updateClasses, newFilter)
    processList(concentration, updateConcentration, newFilter, true)
    processList(ritual, updateRitual, newFilter, true)

    println("New filter level size: " + newFilter.getLevel().size)

    updateFilterState(newFilter)
}

fun onResetAllFiltersClicked(
    spellLevel: List<FilterItem>,
    components: List<FilterItem>,
    saveReq: List<FilterItem>,
    classes: List<FilterItem>,
    concentration: List<FilterItem>,
    ritual: List<FilterItem>,
){
    spellLevel.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    components.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    saveReq.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    classes.forEach { filterItem ->
        filterItem.isSelected.value = false
    }
    concentration.forEach { filterItem ->
        filterItem.isSelected.value = false
    }

    ritual.forEach { filterItem ->
        filterItem.isSelected.value = false
    }
}

fun updateFilterWithSearchName(currentFilter: Filter, searchName: String): Filter {
    val newFilter = Filter()
    newFilter.setSpellName(searchName)

    currentFilter.getLevel().forEach{level ->
        newFilter.addLevel(level)
    }
    currentFilter.getComponent().forEach{component ->
        newFilter.addComponent(component)
    }

    currentFilter.getSaveReq().forEach { saveReq ->
        newFilter.addSaveReq(saveReq)
    }

    currentFilter.getClasses().forEach { classes ->
        newFilter.addClass(classes)
    }
    currentFilter.getIsConcentration().forEach{
        newFilter.addConcentration(it)
    }

    currentFilter.getIsRitual().forEach{
        newFilter.addRitual(it)
    }

    return newFilter
}*/
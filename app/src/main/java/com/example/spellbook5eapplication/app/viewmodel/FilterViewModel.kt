package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter

class FiltersViewModel : ViewModel() {
    private val _currentFilter = mutableStateOf(Filter())
    val currentFilter: State<Filter> = _currentFilter

    private val _resetFiltersSignal = mutableStateOf(false)
    val resetFiltersSignal: State<Boolean> = _resetFiltersSignal

    fun applyFilters(
        spellLevel: List<FilterItem>,
        components: List<FilterItem>,
        saveReq: List<FilterItem>,
        classes: List<FilterItem>,
        concentration: List<FilterItem>,
        ritual: List<FilterItem>
    ) {
        val newFilter = Filter()

        processList(spellLevel) { level -> newFilter.addLevel(level.toInt()) }

        processList(components) { component ->
            when (component) {
                "Verbal" -> newFilter.addComponent(Filter.Component.VERBAL)
                "Somatic" -> newFilter.addComponent(Filter.Component.SOMATIC)
                "Material" -> newFilter.addComponent(Filter.Component.MATERIAL)
            }
        }
        processList(saveReq) { req ->
            when (req) {
                "Strength" -> newFilter.addSaveReq(Filter.SaveReq.STRENGTH)
                "Dexterity" -> newFilter.addSaveReq(Filter.SaveReq.DEXTERITY)
                "Constitution" -> newFilter.addSaveReq(Filter.SaveReq.CONSTITUTION)
                "Wisdom" -> newFilter.addSaveReq(Filter.SaveReq.WISDOM)
                "Intelligence" -> newFilter.addSaveReq(Filter.SaveReq.INTELLIGENCE)
                "Charisma" -> newFilter.addSaveReq(Filter.SaveReq.CHARISMA)
            }
        }
        processList(classes) { cls ->
            when(cls) {
                "Artificer" -> newFilter.addClass(Filter.Classes.ARTIFICER)
                "Barbarian" -> newFilter.addClass(Filter.Classes.BARBARIAN)
                "Bard" -> newFilter.addClass(Filter.Classes.BARD)
                "Cleric" -> newFilter.addClass(Filter.Classes.CLERIC)
                "Druid" -> newFilter.addClass(Filter.Classes.DRUID)
                "Fighter" -> newFilter.addClass(Filter.Classes.FIGHTER)
                "Monk" -> newFilter.addClass(Filter.Classes.MONK)
                "Paladin" -> newFilter.addClass(Filter.Classes.PALADIN)
                "Ranger" -> newFilter.addClass(Filter.Classes.RANGER)
                "Rogue" -> newFilter.addClass(Filter.Classes.ROGUE)
                "Sorcerer" -> newFilter.addClass(Filter.Classes.SORCERER)
                "Warlock" -> newFilter.addClass(Filter.Classes.WARLOCK)
                "Wizard" -> newFilter.addClass(Filter.Classes.WIZARD)
            }
        }
        processList(concentration) { conc ->
            newFilter.addConcentration(conc.toBoolean())
        }
        processList(ritual) { rit ->
            newFilter.addRitual(rit.toBoolean())
        }

        _currentFilter.value = newFilter
    }

    private fun processList(items: List<FilterItem>, updateFilter: (String) -> Unit) {
        items.filter { it.isSelected.value }.forEach { item ->
            updateFilter(item.label)
        }
    }

    fun resetFilters() {
        _resetFiltersSignal.value = true
        // Reset internal ViewModel filter data if any
        // ...
        _resetFiltersSignal.value = false // Reset the signal
    }

    fun updateFilterWithSearchName(searchName: String) {
        val newFilter = Filter()
        newFilter.setSpellName(searchName)

        val currentFilter = _currentFilter.value
        // Copy other criteria from currentFilter to newFilter
        // ...

        _currentFilter.value = newFilter
    }
    // ... other functions ...
}


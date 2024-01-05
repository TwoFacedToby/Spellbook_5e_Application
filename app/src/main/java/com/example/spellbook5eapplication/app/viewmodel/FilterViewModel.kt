package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter

class FilterViewModel : ViewModel() {
    private val _currentFilter = mutableStateOf(Filter())
    val currentFilter: State<Filter> = _currentFilter

    fun applyFilters(
        spellLevel: List<FilterItem>,
        components: List<FilterItem>,
        saveReq: List<FilterItem>,
        classes: List<FilterItem>,
        concentration: List<FilterItem>,
        ritual: List<FilterItem>
    ) {
        val newFilter = Filter()

        processList(spellLevel) { value -> newFilter.addLevel(value.toInt()) }
        processList(components) { value -> newFilter.addComponent(mapComponent(value)) }
        processList(saveReq) { value -> newFilter.addSaveReq(mapSaveReq(value))}
        processList(classes) { value -> newFilter.addClass(mapClasses(value))}
        processList(concentration) { value -> newFilter.addConcentration(value.toBoolean())}
        processList(ritual) { value -> newFilter.addRitual(value.toBoolean())}
        _currentFilter.value = newFilter
    }

    private fun processList(items: List<FilterItem>, updateFilter: (String) -> Unit) {
        items.filter { it.isSelected.value }.forEach { item ->
            updateFilter(item.label)
        }
    }

    private fun mapComponent(component: String): Filter.Component {
        return when (component) {
            "Verbal" -> Filter.Component.VERBAL
            "Semantic" -> Filter.Component.SOMATIC
            "Material" -> Filter.Component.MATERIAL
            else -> throw IllegalArgumentException("Unknown component: $component")
        }
    }

    private fun mapClasses(classes: String): Filter.Classes {
        return when(classes) {
            "Artificer" -> Filter.Classes.ARTIFICER
            "Barbarian" -> Filter.Classes.BARBARIAN
            "Bard" -> Filter.Classes.BARD
            "Cleric" -> Filter.Classes.CLERIC
            "Druid" -> Filter.Classes.DRUID
            "Fighter" -> Filter.Classes.FIGHTER
            "Monk" -> Filter.Classes.MONK
            "Paladin" -> Filter.Classes.PALADIN
            "Ranger" -> Filter.Classes.RANGER
            "Rogue" -> Filter.Classes.ROGUE
            "Sorcerer" -> Filter.Classes.SORCERER
            "Warlock" -> Filter.Classes.WARLOCK
            "Wizard" -> Filter.Classes.WIZARD
            else -> throw IllegalArgumentException("Unknown class: $classes")
        }
    }

    private fun mapSaveReq(saveReq: String): Filter.SaveReq {
        return when(saveReq) {
            "Strenght" -> Filter.SaveReq.STRENGTH
            "Constitution" -> Filter.SaveReq.CONSTITUTION
            "Dexterity" -> Filter.SaveReq.DEXTERITY
            "Wisdom" -> Filter.SaveReq.WISDOM
            "Intelligence" -> Filter.SaveReq.INTELLIGENCE
            "Charisma" -> Filter.SaveReq.CHARISMA
            else -> throw IllegalArgumentException("Unknow save requirement: $saveReq")
        }
    }

    fun resetCurrentFilter() {
        _currentFilter.value = Filter() // Reset to a new, blank Filter instance
    }

    fun updateFilterWithSearchName(searchName: String) {
        val newFilter = Filter()
        newFilter.setSpellName(searchName)

        val current = _currentFilter.value

        // Copying other attributes from current to newFilter
        current.getSchool().forEach { newFilter.addSchool(it) }
        current.getCastingTime().forEach { newFilter.addCastingTime(it) }
        current.getAreaOfEffect().forEach { newFilter.addAreaOfEffect(it) }
        current.getDuration().forEach { newFilter.addDuration(it) }
        current.getComponent().forEach { newFilter.addComponent(it) }
        current.getDamageType().forEach { newFilter.addDamageType(it) }
        current.getLevel().forEach { newFilter.addLevel(it) }
        current.getClasses().forEach { newFilter.addClass(it) }
        current.getIsConcentration().forEach { newFilter.addConcentration(it) }
        current.getIsRitual().forEach { newFilter.addRitual(it) }
        current.getSaveReq().forEach { newFilter.addSaveReq(it) }

        _currentFilter.value = newFilter
    }
}



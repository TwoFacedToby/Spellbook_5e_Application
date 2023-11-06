package com.example.spellbook5eapplication.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter

class FilterViewModel : ViewModel() {


    val spellLevels = (0..9).toList()
    val components = listOf("Verbal", "Semantic", "Material")
    val saves = listOf("Strength", "Dexterity", "Constitution", "Charisma", "Wisdom", "Intelligence")
    val classes = listOf("Artificer", "Barbarian", "Bard", "Cleric", "Druid", "Fighter", "Monk", "Paladin", "Ranger", "Rogue", "Sorcerer", "Warlock", "Wizard")
    val damageTypes = listOf("Acid", "Bludgeoning", "Cold", "Fire", "Force", "Lightning", "Necrotic", "Piercing", "Poison", "Psychic", "Radiant", "Slashing", "Thunder")
    val schools = listOf("Abjuration", "Conjuration", "Divination", "Enchantment", "Evocation", "Illusion", "Necromancy", "Transmutation")
    val castingTimes = listOf("Instantaneous", "1 Action", "1 Bonus Action", "1 Reaction", "1 Minute", "10 Minutes", "1 Hour")
    val areasOfEffect = listOf("Burst", "Cone", "Cylinder", "Line", "Sphere")
    val durations = listOf("Instantaneous", "1 Round", "8 Hours", "24 Hours", "Concentration", "Permanent")
    val concentrations = listOf("Yes", "No")
    val rituals = listOf("Yes", "No")

    // State management for filter selections:

    // Spell levels
    private val _selectedLevels = MutableLiveData<List<Int>>(emptyList())
    val selectedLevels: LiveData<List<Int>> = _selectedLevels

    fun toggleLevelSelection(level: Int) {
        _selectedLevels.value = _selectedLevels.value?.let { selectedLevels ->
            if (selectedLevels.contains(level)) selectedLevels - level else selectedLevels + level
        }
    }

    // Components
    private val _selectedComponents = MutableLiveData<List<String>>(emptyList())
    val selectedComponents: LiveData<List<String>> = _selectedComponents

    fun toggleComponentSelection(component: String) {
        _selectedComponents.value = _selectedComponents.value?.let { selectedComponents ->
            if (selectedComponents.contains(component)) selectedComponents - component else selectedComponents + component
        }
    }

    // Saves
    private val _selectedSaves = MutableLiveData<List<String>>(emptyList())
    val selectedSaves: LiveData<List<String>> = _selectedSaves

    fun toggleSaveSelection(save: String) {
        _selectedSaves.value = _selectedSaves.value?.let { selectedSaves ->
            if (selectedSaves.contains(save)) selectedSaves - save else selectedSaves + save
        }
    }


    // Classes
    private val _selectedClasses = MutableLiveData<List<String>>(emptyList())
    val selectedClasses: LiveData<List<String>> = _selectedClasses

    fun toggleClassSelection(className: String) {
        _selectedClasses.value = _selectedClasses.value?.let { selectedClasses ->
            if (selectedClasses.contains(className)) selectedClasses - className else selectedClasses + className
        }
    }

    // Damage types
    private val _selectedDamageTypes = MutableLiveData<List<String>>(emptyList())
    val selectedDamageTypes: LiveData<List<String>> = _selectedDamageTypes

    fun toggleDamageTypeSelection(damageType: String) {
        _selectedDamageTypes.value = _selectedDamageTypes.value?.let { selectedDamageTypes ->
            if(selectedDamageTypes.contains(damageType)) selectedDamageTypes - damageType else selectedDamageTypes + damageType
        }
    }

    // Schools
    private val _selectedSchools = MutableLiveData<List<String>>(emptyList())
    val selectedSchools: LiveData<List<String>> = _selectedSchools

    fun toggleSchoolSelection(school : String) {
        _selectedSchools.value = _selectedSchools.value?.let { selectedSchools ->
            if(selectedSchools.contains(school)) selectedSchools - school else selectedSchools + school
        }
    }

    // Casting times
    private val _selectedCastingTimes = MutableLiveData<List<String>>(emptyList())
    val selectedCastingTimes: LiveData<List<String>> = _selectedCastingTimes

    fun toggleCastingTimeSelection(castingTime: String){
        _selectedCastingTimes.value = _selectedCastingTimes.value?.let { selectedCastingTimes ->
            if(selectedCastingTimes.contains(castingTime)) selectedCastingTimes - castingTime else selectedCastingTimes + castingTime
        }
    }

    // Areas of effect
    private val _selectedAreasOfEffect = MutableLiveData<List<String>>(emptyList())
    val selectedAreasOfEffect: LiveData<List<String>> = _selectedAreasOfEffect

    fun toggleAreaOfEffectSelection(areaOfEffect: String) {
        _selectedAreasOfEffect.value = _selectedAreasOfEffect.value?.let { selectedAreasOfEffect ->
            if (selectedAreasOfEffect.contains(areaOfEffect)) selectedAreasOfEffect - areaOfEffect else selectedAreasOfEffect + areaOfEffect
        }
    }

    // Durations
    private val _selectedDurations = MutableLiveData<List<String>>(emptyList())
    val selectedDurations: LiveData<List<String>> = _selectedDurations

    fun toggleDurationSelection(duration: String) {
        _selectedDurations.value = _selectedDurations.value?.let { selectedDurations ->
            if (selectedDurations.contains(duration)) selectedDurations - duration else selectedDurations + duration
        }
    }

    // Concentration
    private val _isConcentrationSelected = MutableLiveData<Boolean>(false)
    val isConcentrationSelected: LiveData<Boolean> = _isConcentrationSelected

    fun toggleConcentrationSelection() {
        _isConcentrationSelected.value = _isConcentrationSelected.value?.not()
    }

    // Ritual
    private val _isRitualSelected = MutableLiveData<Boolean>(false)
    val isRitualSelected: LiveData<Boolean> = _isRitualSelected

    fun toggleRitualSelection() {
        _isRitualSelected.value = _isRitualSelected.value?.not()
    }

    fun createFilterFromSelections(): Filter {
        val filter = Filter()

        // Assume the LiveData objects hold the necessary information and enums are correctly matched by string values
        selectedSchools.value?.mapNotNull { schoolString ->
            Filter.School.values().firstOrNull { it.value == schoolString }
        }?.forEach { school ->
            filter.addSchool(school)
        }

        selectedCastingTimes.value?.mapNotNull { castingTimeString ->
            Filter.Casting_Time.values().firstOrNull { it.value == castingTimeString }
        }?.forEach { castingTime ->
            filter.addCastingTime(castingTime)
        }

        selectedAreasOfEffect.value?.mapNotNull { areaOfString ->
            Filter.Area_Of_Effect.values().firstOrNull { it.value == areaOfString }
        }?.forEach { areaOfEffect ->
            filter.addAreaOfEffect(areaOfEffect)
        }

        selectedDurations.value?.mapNotNull { durationString ->
            Filter.Duration.values().firstOrNull { it.value == durationString }
        }?.forEach { duration ->
            filter.addDuration(duration)
        }

        selectedComponents.value?.mapNotNull { componentString ->
            Filter.Component.values().firstOrNull { it.value == componentString }
        }?.forEach { component ->
            filter.addComponent(component)
        }

        selectedDamageTypes.value?.mapNotNull { damageTypeString ->
            Filter.Damage_Type.values().firstOrNull { it.value == damageTypeString }
        }?.forEach { damageType ->
            filter.addDamageType(damageType)
        }

        selectedClasses.value?.mapNotNull { classString ->
            Filter.Classes.values().firstOrNull { it.value == classString }
        }?.forEach { charClass ->
            filter.addClass(charClass)
        }

        // For boolean filters like Ritual and Concentration, we handle them differently as they are not enum
        isConcentrationSelected.value?.let { isRitual ->
            if(isRitual) filter.addRitual(isRitual)
        }

        isRitualSelected.value?.let { isConcentration ->
            if(isConcentration) filter.addConcentration(isConcentration)
        }

        // For levels, since they are integers, we add them directly
        selectedLevels.value?.forEach { level ->
            filter.addLevel(level)
        }

        // Set the spell name if there is one selected
        //filter.setSpellName(spellName.value ?: "")
        return filter
    }
}
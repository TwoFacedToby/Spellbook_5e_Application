package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class FilterItem(
    val label: String,  var isSelected: MutableState<Boolean>
) {
    companion object {

        val spellLevels = listOf(
            FilterItem("0", mutableStateOf(false)),
            FilterItem("1", mutableStateOf(false)),
            FilterItem("2", mutableStateOf(false)),
            FilterItem("3", mutableStateOf(false)),
            FilterItem("4", mutableStateOf(false)),
            FilterItem("5", mutableStateOf(false)),
            FilterItem("6", mutableStateOf(false)),
            FilterItem("7", mutableStateOf(false)),
            FilterItem("8", mutableStateOf(false)),
            FilterItem("9", mutableStateOf(false)),
        )

        val components = listOf(
            FilterItem("Verbal", mutableStateOf(false)),
            FilterItem("Semantic", mutableStateOf(false)),
            FilterItem("Material", mutableStateOf(false)),
        )

        val saveReq = listOf(
            FilterItem("Strength", mutableStateOf(false)),
            FilterItem("Dexterity", mutableStateOf(false)),
            FilterItem("Constitution", mutableStateOf(false)),
            FilterItem("Charisma", mutableStateOf(false)),
            FilterItem("Wisdom", mutableStateOf(false)),
            FilterItem("Intelligence", mutableStateOf(false)),
        )

        val classes = listOf(
            FilterItem("Bard", mutableStateOf(false)),
            FilterItem("Cleric", mutableStateOf(false)),
            FilterItem("Druid", mutableStateOf(false)),
            FilterItem("Fighter", mutableStateOf(false)),
            FilterItem("Monk", mutableStateOf(false)),
            FilterItem("Paladin", mutableStateOf(false)),
            FilterItem("Ranger", mutableStateOf(false)),
            FilterItem("Rogue", mutableStateOf(false)),
            FilterItem("Sorcerer", mutableStateOf(false)),
            FilterItem("Warlock", mutableStateOf(false)),
            FilterItem("Wizard", mutableStateOf(false)),
        )

        val isConcentration = listOf(
            FilterItem("Yes", mutableStateOf(false)),
            FilterItem("No", mutableStateOf(false)),
        )

        val isRitual = listOf(
            FilterItem("Yes", mutableStateOf(false)),
            FilterItem("No", mutableStateOf(false)),
        )
    }
}
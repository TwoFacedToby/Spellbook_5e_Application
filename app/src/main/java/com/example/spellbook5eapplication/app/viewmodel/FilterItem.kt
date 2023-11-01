package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class FilterItem(
    val label: String,  var isSelected: MutableState<Boolean>
)

val level0 = FilterItem("0", mutableStateOf(false))
val level1 = FilterItem("1", mutableStateOf(false))
val level2 = FilterItem("2", mutableStateOf(false))
val level3 = FilterItem("3", mutableStateOf(false))
val level4 = FilterItem("4", mutableStateOf(false))
val level5 = FilterItem("5", mutableStateOf(false))
val level6 = FilterItem("6", mutableStateOf(false))
val level7 = FilterItem("7", mutableStateOf(false))
val level8 = FilterItem("8", mutableStateOf(false))
val level9 = FilterItem("9", mutableStateOf(false))

val verbal = FilterItem("Verbal", mutableStateOf(false))
val semantic = FilterItem("Semantic", mutableStateOf(false))
val matrial = FilterItem("Material", mutableStateOf(false))
val strength = FilterItem("Strength", mutableStateOf(false))
val dexterity = FilterItem("Dexterity", mutableStateOf(false))
val constitution = FilterItem("Constitution", mutableStateOf(false))
val charisma = FilterItem("Charisma", mutableStateOf(false))
val wisdom = FilterItem("Wisdom", mutableStateOf(false))
val intelligence = FilterItem("Intelligence", mutableStateOf(false))

val artificer = FilterItem("Artificer", mutableStateOf(false))
val bard = FilterItem("Bard", mutableStateOf(false))
val cleric = FilterItem("Cleric", mutableStateOf(false))
val druid = FilterItem("Druid", mutableStateOf(false))

val yesConcentration = FilterItem("Yes", mutableStateOf(false))
val noConcentration = FilterItem("No", mutableStateOf(false))

val yesRitual = FilterItem("Yes", mutableStateOf(false))
val noRitual = FilterItem("No", mutableStateOf(false))
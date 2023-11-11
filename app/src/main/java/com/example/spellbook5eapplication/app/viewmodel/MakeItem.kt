package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MakeItem(
    val label: String,  var isSelected: MutableState<Boolean>
)

val mlevel0 = MakeItem("0", mutableStateOf(false))
val mlevel1 = MakeItem("1", mutableStateOf(false))
val mlevel2 = MakeItem("2", mutableStateOf(false))
val mlevel3 = MakeItem("3", mutableStateOf(false))
val mlevel4 = MakeItem("4", mutableStateOf(false))
val mlevel5 = MakeItem("5", mutableStateOf(false))
val mlevel6 = MakeItem("6", mutableStateOf(false))
val mlevel7 = MakeItem("7", mutableStateOf(false))
val mlevel8 = MakeItem("8", mutableStateOf(false))
val mlevel9 = MakeItem("9", mutableStateOf(false))

val mverbal = MakeItem("Verbal", mutableStateOf(false))
val msemantic = MakeItem("Semantic", mutableStateOf(false))
val mmatrial = MakeItem("Material", mutableStateOf(false))
val mstrength = MakeItem("Strength", mutableStateOf(false))
val mdexterity = MakeItem("Dexterity", mutableStateOf(false))
val mconstitution = MakeItem("Constitution", mutableStateOf(false))
val mcharisma = MakeItem("Charisma", mutableStateOf(false))
val mwisdom = MakeItem("Wisdom", mutableStateOf(false))
val mintelligence = MakeItem("Intelligence", mutableStateOf(false))

val martificer = MakeItem("Artificer", mutableStateOf(false))
val mbard = MakeItem("Bard", mutableStateOf(false))
val mcleric = MakeItem("Cleric", mutableStateOf(false))
val mdruid = MakeItem("Druid", mutableStateOf(false))

val myesConcentration = MakeItem("Yes", mutableStateOf(false))
val mnoConcentration = MakeItem("No", mutableStateOf(false))

val myesRitual = MakeItem("Yes", mutableStateOf(false))
val mnoRitual = MakeItem("No", mutableStateOf(false))

val mnoRange = MakeItem("<5", mutableStateOf(false))
val mlowRange = MakeItem("5-10", mutableStateOf(false))
val mmediumRange = MakeItem("10-20", mutableStateOf(false))
val mHighRange = MakeItem("20+", mutableStateOf(false))
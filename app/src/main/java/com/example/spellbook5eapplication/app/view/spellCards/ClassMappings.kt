package com.example.spellbook5eapplication.app.view.spellCards

import com.example.spellbook5eapplication.R

object ClassMappings {

    val classImageMap = mapOf(
        "Artificer" to R.drawable.artificer,
        "Barbarian" to R.drawable.barbarian,
        "Bard" to R.drawable.bard,
        "Cleric" to R.drawable.cleric,
        "Druid" to R.drawable.druid2,
        "Fighter" to R.drawable.fighter,
        "Monk" to R.drawable.monk,
        "Paladin" to R.drawable.paladin,
        "Ranger" to R.drawable.ranger,
        "Rogue" to R.drawable.rouge,
        "Sorcerer" to R.drawable.sorcerer,
        "Warlock" to R.drawable.warlock,
        "Wizard" to R.drawable.wizard
    )

    fun getDrawableResourceForClass(className: String?): Int {
        return classImageMap[className] ?: R.drawable.druid2 // Provide default if not found
    }
}
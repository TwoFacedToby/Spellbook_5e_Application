package com.example.spellbook5eapplication.app.view.spellCards

import com.example.spellbook5eapplication.R
object SchoolMappings {

    val schoolImageMap = mapOf(
        "Evocation" to R.drawable.evocation,
        "Abjuration" to R.drawable.abjuration,
        "Divination" to R.drawable.divination,
        "Transmutation" to R.drawable.transmutation,
        "Enchantment" to R.drawable.enchantment,
        "Illusion" to R.drawable.illusion,
        "Necromancy" to R.drawable.necromancy,
        "Transmutation" to R.drawable.transmutation,
    )

    fun getDrawableResourceForSchool(schoolName: String?): Int {
        return schoolImageMap[schoolName] ?: R.drawable.abjuration // Provide default if not found
    }
}
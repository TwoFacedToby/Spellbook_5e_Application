package com.example.spellbook5eapplication.app.view.spellCards

import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell


class SpellCardCreation(spell: Spell.SpellInfo) {


    val classImageIDs: MutableList<Int> = emptyList<Int>().toMutableList()
    var schoolID = R.drawable.abjuration

    init {
        if (spell.classes != null) setClassImages(spell.classes!!)
        if (spell.school != null) setSchoolImage(spell.school!!)
    }

    private fun setClassImages(classes: List<Spell.SpellClass>) {
        classImageIDs.clear()
        for (c in classes) {
            when (c.name) {
                null -> println("null class")
                "Artificer" -> classImageIDs.add(R.drawable.artificer)
                "Barbarian" -> classImageIDs.add(R.drawable.barbarian)
                "Bard" -> classImageIDs.add(R.drawable.bard)
                "Cleric" -> classImageIDs.add(R.drawable.cleric)
                "Druid" -> classImageIDs.add(R.drawable.druid)
                "Fighter" -> classImageIDs.add(R.drawable.fighter)
                "Monk" -> classImageIDs.add(R.drawable.monk)
                "Paladin" -> classImageIDs.add(R.drawable.paladin)
                "Ranger" -> classImageIDs.add(R.drawable.ranger)
                "Rogue" -> classImageIDs.add(R.drawable.rouge)
                "Sorcerer" -> classImageIDs.add(R.drawable.sorcerer)
                "Warlock" -> classImageIDs.add(R.drawable.warlock)
                "Wizard" -> classImageIDs.add(R.drawable.wizard)
            }
        }
    }

    // TODO // set school ids to actual right images once they've been created.
    private fun setSchoolImage(school: Spell.SpellSchool) {
        when (school.name) {
            null -> println("null school")
            "Abjuration" -> schoolID = R.drawable.abjuration
            "Conjuration" -> schoolID = R.drawable.conjuration
            "Divination" -> schoolID = R.drawable.divination
            "Enchantment" -> schoolID = R.drawable.enchantment
            "Evocation" -> schoolID = R.drawable.evocation
            "Illusion" -> schoolID = R.drawable.illusion
            "Necromancy" -> schoolID = R.drawable.necromancy
            "Transmutation" -> schoolID = R.drawable.transmutation
        }
    }
}
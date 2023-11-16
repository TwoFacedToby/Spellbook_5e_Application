package com.example.spellbook5eapplication.app.view.spellCards

import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info


class SpellCardCreation(spell: Spell_Info.SpellInfo) {


    val classImageIDs : MutableList<Int> = emptyList<Int>().toMutableList()
    var schoolID = R.drawable.abjuration

    init {
        if(spell.classes != null) setClassImages(spell.classes)
        if(spell.school != null) setSchoolImage(spell.school)
    }
    // TODO // set class ids to actual right images once they've been created.

    private fun setClassImages(classes : List<Spell_Info.SpellClass>){
        classImageIDs.clear()
        for(c in classes){
            when (c.name) {
                null -> println("null class")
                "Artificer" -> classImageIDs.add(R.drawable.druid2)
                "Barbarian" -> classImageIDs.add(R.drawable.druid2)
                "Bard" -> classImageIDs.add(R.drawable.druid2)
                "Cleric" -> classImageIDs.add(R.drawable.druid2)
                "Druid" -> classImageIDs.add(R.drawable.druid2)
                "Fighter" -> classImageIDs.add(R.drawable.druid2)
                "Monk" -> classImageIDs.add(R.drawable.druid2)
                "Paladin" ->classImageIDs.add(R.drawable.druid2)
                "Ranger" -> classImageIDs.add(R.drawable.druid2)
                "Rogue" -> classImageIDs.add(R.drawable.druid2)
                "Sorcerer" -> classImageIDs.add(R.drawable.druid2)
                "Warlock" -> classImageIDs.add(R.drawable.druid2)
                "Wizard" -> classImageIDs.add(R.drawable.druid2)
            }
        }
    }
    // TODO // set school ids to actual right images once they've been created.
    private fun setSchoolImage(school: Spell_Info.SpellSchool) {
        when(school.name){
            null -> println("null school")
            "Abjuration" -> schoolID = R.drawable.abjuration
            "Conjuration" -> schoolID = R.drawable.abjuration
            "Divination" -> schoolID = R.drawable.abjuration
            "Enchantment" -> schoolID = R.drawable.abjuration
            "Evocation" -> schoolID = R.drawable.abjuration
            "Illusion" -> schoolID = R.drawable.abjuration
            "Necromancy" -> schoolID = R.drawable.abjuration
            "Transmutation" -> schoolID = R.drawable.abjuration
        }
    }
}
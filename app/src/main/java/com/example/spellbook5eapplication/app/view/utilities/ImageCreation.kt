package com.example.spellbook5eapplication.app.view.utilities

import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell


class ImageCreation(spell: Spell) {

    init {
        setClassImages(spell)
        setSchoolImage(spell)
    }

    private fun setClassImages(spell: Spell){
        //classImageIDs.clear()
        val classImageIDs : MutableList<Int> = emptyList<Int>().toMutableList()
        for(c in spell.classes){
            when (c) {
                "Artificer" -> classImageIDs.add(R.drawable.artificer)
                "Barbarian" -> classImageIDs.add(R.drawable.barbarian)
                "Bard" -> classImageIDs.add(R.drawable.bard)
                "Cleric" -> classImageIDs.add(R.drawable.cleric)
                "Druid" -> classImageIDs.add(R.drawable.druid)
                "Fighter" -> classImageIDs.add(R.drawable.fighter)
                "Monk" -> classImageIDs.add(R.drawable.monk)
                "Paladin" ->classImageIDs.add(R.drawable.paladin)
                "Ranger" -> classImageIDs.add(R.drawable.ranger)
                "Rogue" -> classImageIDs.add(R.drawable.rouge)
                "Sorcerer" -> classImageIDs.add(R.drawable.sorcerer)
                "Warlock" -> classImageIDs.add(R.drawable.warlock)
                "Wizard" -> classImageIDs.add(R.drawable.wizard)
            }
        }
        spell.classImages = classImageIDs
    }
    private fun setSchoolImage(spell: Spell) {
        when(spell.school){
            "Abjuration" -> spell.schoolImage = R.drawable.abjuration
            "Conjuration" -> spell.schoolImage = R.drawable.conjuration
            "Divination" -> spell.schoolImage = R.drawable.divination
            "Enchantment" -> spell.schoolImage = R.drawable.enchantment
            "Evocation" -> spell.schoolImage = R.drawable.evocation
            "Illusion" -> spell.schoolImage = R.drawable.illusion
            "Necromancy" -> spell.schoolImage = R.drawable.necromancy
            "Transmutation" -> spell.schoolImage = R.drawable.transmutation
        }
    }
}
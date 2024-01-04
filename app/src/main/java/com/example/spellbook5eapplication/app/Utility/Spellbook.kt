package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Utility.Displayable

class Spellbook(var spellbookName: String): Displayable {
    val spells: MutableList<String> = mutableListOf()

    fun addSpellToSpellbook(spellName: String) {
        if (!spells.contains(spellName)) {
            spells.add(spellName)
        }
    }


    fun removeSpell(spellName: String) {
        spells.remove(spellName)
    }

    override fun toString(): String {
        return "Spellbook(spellbookName='$spellbookName', spells=$spells)"
    }


}

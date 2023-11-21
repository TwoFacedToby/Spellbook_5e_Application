package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Utility.SpellbookManager

class Spellbook(var spellbookName: String) {
    val spells: MutableList<String> = mutableListOf()

    fun addSpellToFavourites(spellName: String) {
        if (!spells.contains(spellName)) {
            spells.add(spellName)
        }
    }



    fun removeSpell(spellName: String) {
        spells.remove(spellName)
    }

}

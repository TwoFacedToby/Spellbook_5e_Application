package com.example.spellbook5eapplication.app.Model

class Spellbook(var spellbookName: String) {
    val spells: MutableList<String> = mutableListOf()

    fun addSpellToSpellbook(spellName: String) {
        if (!spells.contains(spellName)) {
            spells.add(spellName)
        }
    }




    fun removeSpell(spellName: String) {
        spells.remove(spellName)
    }

}

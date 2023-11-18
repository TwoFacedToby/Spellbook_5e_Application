package com.example.spellbook5eapplication.app.Model

class Spellbook(var spellbookName: String) {
    val spells: MutableList<String> = mutableListOf()

    fun addSpell(spellName: String) {
        spells.add(spellName)
    }

    fun removeSpell(spellName: String) {
        spells.remove(spellName)
    }
}

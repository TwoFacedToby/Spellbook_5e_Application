package com.example.spellbook5eapplication.app.Utility

import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.google.gson.Gson
import java.io.File

object SpellbookManager {
    private val spellbooks: MutableList<Spellbook> = mutableListOf()

    fun addSpellbook(spellbook: Spellbook) {
        spellbooks.add(spellbook)
    }

    fun removeSpellbook(spellbookName: String) {
        spellbooks.removeAll { it.spellbookName == spellbookName }
    }

    fun getSpellbook(spellbookName: String): Spellbook? {
        return spellbooks.find { it.spellbookName == spellbookName }
    }

    fun saveSpellbookToFile(spellBookName: String) {
        val spellbook = getSpellbook(spellBookName)
        spellbook?.let {
            val gson = Gson()
            val spellbookJson = gson.toJson(it)

            SpellController.saveJsonToFile(spellbookJson, "Spellbooks", spellBookName + ".json")
        }
    }

    fun printAllSpellbooks() {
        spellbooks.forEach { spellbook ->
            println("Spellbook: ${spellbook.spellbookName}")
            spellbook.spells.forEach { spell ->
                println("- $spell")
            }
        }
    }


    fun getAllSpellbooks(): List<Spellbook> {
        println(spellbooks.toList())
        return spellbooks.toList()
    }
}

package com.example.spellbook5eapplication.app.Utility

import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.google.gson.Gson
import java.io.File

class HomeBrewManager {
    private val homebrew: MutableList<Spell_Info.SpellInfo> = mutableListOf()

    fun saveHomeBrewToFile(spellName: String, spell: Spell_Info.SpellInfo) {
        //val homebrew = getSpellbook(spellBookName)
        homebrew?.let {
            val gson = Gson()
            val spellbookJson = gson.toJson(spell)

            SpellController.saveJsonToFile(spellbookJson, "HomeBrews", spellName + ".json")
        }
    }

    fun loadSpellFromHomeBrew() {
        val json = File("HomeBrews").readText()
        val loadedHomeBrews = Gson().fromJson(json, Array<Spell_Info.SpellInfo>::class.java).toList()
        homebrew.clear()
        homebrew.addAll(loadedHomeBrews)
    }

    fun printAllSpellbooks() {
        homebrew.forEach { spell ->
            println("Spell: ${spell.name}")
            println("- $spell")
        }
    }

    fun getAllSpellbooks(): List<Spell_Info.SpellInfo> {
        return homebrew.toList()
    }
}

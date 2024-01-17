package com.example.spellbook5eapplication.app.Repository

import android.util.Log
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.gson.Gson

object SpellbookManager {
    private val spellbooks: MutableList<Spellbook> = mutableListOf()

    fun addSpellbook(spellbook: Spellbook) {
        spellbooks.add(spellbook)
    }

    fun removeSpellbook(spellbookName: String) {
        var delete = LocalDataLoader.deleteFile(spellbookName.lowercase(), LocalDataLoader.DataType.SPELLBOOK)
        Log.d("DIDIT", delete.toString())
        if(delete){
            spellbooks.removeAll { it.spellbookName == spellbookName }
        }

    }

    fun removeSpellFromSpellbook(spellbookName: String, spell: Spell.SpellInfo){

        val spellbook = getSpellbook(spellbookName)

        spellbook!!.spells.remove(spell.index)

        saveSpellbookToFile(spellbookName)

    }

    fun getSpellbook(spellbookName: String): Spellbook? {
        return spellbooks.find { it.spellbookName == spellbookName
        }
    }

    fun saveSpellbookToFile(spellBookName: String) {
        val spellbook = getSpellbook(spellBookName)
        Log.d("LOLOL100", spellBookName)
        Log.d("LOLOL300", spellbook.toString())
        spellbook?.let {
            val gson = Gson()
            val spellbookJson = gson.toJson(it)

            Log.d("LOLOL300", spellbookJson)

            LocalDataLoader.saveJson(
                spellbookJson,
                spellBookName.lowercase(),
                LocalDataLoader.DataType.SPELLBOOK
            )
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
        Log.d("LOLO", spellbooks.toList().toString())

        // Removing "favourite spellbook" from Spellbooklist
        return spellbooks.filter { it.spellbookName != "Favourites" }
    }
}

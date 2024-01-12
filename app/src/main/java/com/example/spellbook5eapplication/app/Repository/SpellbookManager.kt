package com.example.spellbook5eapplication.app.Repository

import android.util.Log
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.gson.Gson

object SpellbookManager {
    private val spellbooks: MutableList<Spellbook> = mutableListOf()

    fun addSpellbook(spellbook: Spellbook) {
        spellbooks.add(spellbook)
    }

    fun removeSpellbook(spellbookName: String) {
        spellbooks.removeAll { it.spellbookName == spellbookName }
    }

    fun getSpellbook(spellbookName: String): Spellbook? {
        Log.d("LOLOL", spellbooks.toString())
        Log.d("TESTITEST", spellbooks.toString())
        return spellbooks.find { it.spellbookName == spellbookName }
    }

    fun saveSpellbookToFile(spellBookName: String) {
        val spellbook = getSpellbook(spellBookName)
        Log.d("LOLOL", spellBookName)
        spellbook?.let {
            val gson = Gson()
            val spellbookJson = gson.toJson(it)

            LocalDataLoader.saveJson(
                spellbookJson,
                spellBookName,
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
        return spellbooks.toList()
    }
}

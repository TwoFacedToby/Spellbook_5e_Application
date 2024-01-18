package com.example.spellbook5eapplication.app.Repository

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SpellbookManager {
    private val spellbooks: MutableList<Spellbook> = mutableListOf()

    fun removeHomebrewFromSpellbook(index: String){
        spellbooks.forEach{book ->
            book.spells.forEach {
                if(it.contains(index)){
                    book.removeSpell(index)
                }
            }
        }
    }

    fun addSpellbook(spellbook: Spellbook) {
        spellbooks.add(spellbook)
    }

    fun removeSpellbook(spellbookName: String) {
        var delete = LocalDataLoader.deleteFile(spellbookName.lowercase(), LocalDataLoader.DataType.SPELLBOOK)
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

        CoroutineScope(Dispatchers.IO).launch {

            val spellbook = getSpellbook(spellBookName)
            spellbook?.let {
                val gson = Gson()
                val spellbookJson = gson.toJson(it)

                LocalDataLoader.saveJson(
                    spellbookJson,
                    spellBookName.lowercase(),
                    LocalDataLoader.DataType.SPELLBOOK
                )
            }
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

        // Removing "favourite spellbook" from Spellbooklist
        return spellbooks.filter { it.spellbookName != "favourites" }
    }
}

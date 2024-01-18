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
        Log.d("ADDED", spellbook.toString())
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

        CoroutineScope(Dispatchers.IO).launch {

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
        return spellbooks.filter { it.spellbookName != "favourites" }
    }
}

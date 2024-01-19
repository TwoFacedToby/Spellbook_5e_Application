package com.example.spellbook5eapplication.app.Repository
import android.util.Log
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.gson.Gson
import java.io.File


object SpelllistLoader {

    /**
     * Load a spellbook from a JSON file and convert it to a SpellList.
     * @param filePath The path to the spellbook JSON file.
     * @param spellController The SpellController instance to use for loading spell details.
     * @return A SpellList containing the spells from the spellbook.
     */
    suspend fun loadSpellbookAsSpellList(index: String): SpellList {
        // Read the spellbook JSON
        val list = LocalDataLoader.getSpellBookIndexList(index)
        val spellbook = Spellbook(index)
        list.forEach {
            spellbook.addSpellToSpellbook(it)
        }

        // Get detailed spell info for each spell name
        val spellInfoList = spellbook.spells.mapNotNull { spellName ->
            SpellDataFetcher.localOrAPI(spellName)
        }

        // Create and populate the SpellList
        val spellList = SpellList()
        spellList.setIndexList(spellbook.spells)
        spellList.setSpellInfoList(spellInfoList)

        return spellList
    }

    fun loadSpellbooks() {
        val listOfNames = LocalDataLoader.getIndexList(LocalDataLoader.DataType.SPELLBOOK)
        listOfNames.forEach{
            val json = LocalDataLoader.getJson(it, LocalDataLoader.DataType.SPELLBOOK)
            val spellbook = Gson().fromJson(json, Spellbook::class.java)
            if (spellbook != null) {
                if (SpellbookManager.getSpellbook(spellbook.spellbookName) == null) {
                    SpellbookManager.addSpellbook(spellbook)
                }
            }
        }
    }
    suspend fun loadHomeBrewSpellList() : SpellList{
        val spellList = SpellList()
        spellList.setIndexList(LocalDataLoader.getIndexList(LocalDataLoader.DataType.HOMEBREW))
        val spellInfoList = spellList.getIndexList().mapNotNull { spellName ->
            SpellDataFetcher.localOrAPI(spellName)
        }
        spellList.setSpellInfoList(spellInfoList)
        return spellList
    }


}

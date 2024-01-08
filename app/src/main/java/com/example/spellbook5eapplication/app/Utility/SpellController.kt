package com.example.spellbook5eapplication.app.Utility

import com.example.spellbook5eapplication.app.Model.API
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.JSON
import com.example.spellbook5eapplication.app.Model.JSON_to_Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

object SpellController {
    private val api = API()
    private val jsonToSpell = JSON_to_Spell()



    suspend fun getAllSpellsList(): SpellList? {
        var list: SpellList? = null
        try {
            val json = api.getListOfSpells()
            if (json != null) {
                list = jsonToSpell.jsonToSpellList(json)
                LocalDataLoader.saveIndexList(LocalDataLoader.DataType.INDIVIDUAL, list.getIndexList())
            }
            else{
                val localList = LocalDataLoader.getIndexList(LocalDataLoader.DataType.INDIVIDUAL)
                if(localList.isNullOrEmpty()){
                    val spellList = SpellList()
                    spellList.setIndexList(localList)
                    list = spellList
                }
            }
        } catch (e: Exception) {
            println("An error occurred: ${e.message}")
        }
        return list
    }
    /**@author Tobias s224271
     * @param spellName The index of the spell, that can be called to the api.
     * @return The converted JSON in a data model class called Spell_Info.SpellInfo
     *
     * Starts the request for getting spell info from the API. This function might take a while to return, so make sure you run it on another thread than main.
     */
    fun getSpellFromName(spellName : String) : Spell_Info.SpellInfo? {
        var spell: Spell_Info.SpellInfo? = null
        runBlocking {
            try {
                val json = getJson(spellName)
                if(json != null) spell = jsonToSpell.jsonToSpell(json)
                else println("\n\n\n####### ERROR ########\n Failed to fetch spell information.\n Spell does not exist in local storage or api \n#####################\n\n\n")
            } catch (e: Exception) {
                println("An error occurred: ${e.message}")
            }
        }
        return spell
    }
    /**@author Tobias s224271
     * @param amount The amount of spells we want to load
     * @param spellList The Spelllist that needs to be loaded from
     * @return List<Spell_Info.SpellInfo?> if there were spells to be loaded. If there were less than the amount given, it only returns the amount left. If none are left it returns null.
     *
     * Loads the next spells from the spelllist from the api. This is made to work with pagination.
     */
    fun loadNextFromSpellList(amount : Int, spellList: SpellList) : List<Spell_Info.SpellInfo?>? {

        val current = if(spellList.getLoaded() == 0) 0 else spellList.getLoaded() + 1;
        if (spellList.getIndexList().size <= current) return null
        val list: MutableList<String> = emptyList<String>().toMutableList()

        if (spellList.getIndexList().size > current + amount) {
            for (i in current until current + amount) {
                list.add(spellList.getIndexList()[i])
            }
        } else {
            val to = spellList.getIndexList().size - 1
            for (i in current..to) {
                list.add(spellList.getIndexList()[i])
            }
        }
        val nextSpells = loadSpells(list)
        spellList.setSpellInfoList(spellList.getSpellInfoList() + nextSpells)
        spellList.setLoaded(spellList.getLoaded() + nextSpells.size)
        return nextSpells
    }
    /**@author Tobias s224271
     * @param list List of String indexes of spells.
     * @return List of JSON in the form of strings
     *
     * In a couroutineScope we ask for each spell and waits for all to be resieved before returning the list.
     *
     */
    private suspend fun getJsonsFromIndexes(list : List<String>) : List<String?>{
        return coroutineScope {
            list.map { spellName ->
                async {
                    getJson(spellName)
                }
            }.awaitAll()
        }
    }
    /**@author Tobias s224271, Kenneth s221064
     * @param index the index of the spell to load a json for
     * @return The String format of JSON or null if not found in locale, database or api.
     *
     * Checks for and returns if spell is found in homebrew local storage
     * Checks for and returns if spell is found in local storage
     * Checks for and returns if spell is found in the api
     * else returns null
     */
    private suspend fun getJson(index: String): String? {
        var str : String? = LocalDataLoader.getJson(index, LocalDataLoader.DataType.HOMEBREW) //Try to find in Homebrew
        if(str != null) return str //Return if found
        str = LocalDataLoader.getJson(index, LocalDataLoader.DataType.INDIVIDUAL) //Try to find in saved data
        if(str != null) return str //Return if found
        str = api.getSpellFromApiWithRetry(index, 10) //Try to find in api
        if(str != null) LocalDataLoader.saveJson(str, index, LocalDataLoader.DataType.INDIVIDUAL) //Save to device for later use if found
        return str //Return either json string or null if not found
    }
    /**@author Tobias s224271
     * @param indexes A list of string indexes that needs to be searched for and loaded
     * @return A List of SpellInfo from of those spells named with indexes
     *
     * Creates a list for the string jsons
     * Requests for the jsons using getJSONFromList() which checks local storage and api for jsons.
     * Converts each json into a SpellInfo data class and inserts into list
     * Returns the list
     */
    private fun loadSpells(indexes: List<String>): List<Spell_Info.SpellInfo> {
        var spellInfoJson: List<String?>
        runBlocking {
            spellInfoJson = getJsonsFromIndexes(indexes)
        }
        if (spellInfoJson.isEmpty()) return emptyList();
        val spellInfoList = mutableListOf<Spell_Info.SpellInfo>()
        for (spell in spellInfoJson) {
            if (spell != null) {
                val spellInfo = jsonToSpell.jsonToSpell(spell)
                if (spellInfo != null) spellInfoList.add(spellInfo);
            }
        }
        return spellInfoList.toList();
    }

}
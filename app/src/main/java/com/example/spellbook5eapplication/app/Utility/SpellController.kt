package com.example.spellbook5eapplication.app.Utility

import android.content.Context
import com.example.spellbook5eapplication.app.Model.API
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.JSON_to_Spell
import com.example.spellbook5eapplication.app.Model.Search
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File

import org.json.JSONObject
class SpellController(private val context: Context) {

    private val api = API()
    private val jsonToSpell = JSON_to_Spell()
    private val search = Search()
    private val localList = extractIndexListFromFile(context, "LocalJSONData/spells.json")

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
                val deferredSpellInfo: Deferred<String?> = async(Dispatchers.IO) {
                    api.getSpellFromApi(spellName)
                }
                val spellInfo: String? = deferredSpellInfo.await()
                if (spellInfo != null) {
                    spell = jsonToSpell.jsonToSpell(spellInfo)
                } else {
                    println("Failed to fetch spell information.")
                }
            } catch (e: Exception) {
                println("An error occurred: ${e.message}")
            }
        }
        if(spell != null) return spell
        return null
    }
    /**@author Tobias s224271
     * @return A spellList with a list of all spells.
     *
     * Creates a SpellList
     * Gets a json with all spell-names from the api.
     * convers the json to a list
     * inserts the list into the SpellList
     *
     */
    fun getAllSpellsList() : SpellList?{
        var list : SpellList? = null
        runBlocking {
            try {
                val json = api.getListOfSpells()
                if(json != null){
                    println(localList)
                    saveJsonToFile(context, json, "LocalJSONData", "spells.json")
                    list = jsonToSpell.jsonToSpellList(json)
                }
            } catch (e: Exception) {
                println("An error occurred: ${e.message}")
            }
        }
        if(list != null) return list
        return null
    }





    /**
     * Author: Kenneth Kaiser
     * Desc: Saves a json string on the PC in a specific folder
     */
    fun saveJsonToFile(context: Context, json: String, directoryName: String, fileName: String) {
        try {
            // Create the directory in the internal storage
            val directory = File(context.filesDir, directoryName)
            if (!directory.exists()) {
                directory.mkdirs() // Make the directory if it does not exist
            }

            // Create the file within the new directory
            val file = File(directory, fileName)
            file.writeText(json) // Write the JSON string to the file

            println("JSON saved to ${file.absolutePath}")
        } catch (e: Exception) {
            println("Failed to save JSON to file: ${e.message}")
        }
    }
    /**@author Tobias s224271
     * @param spellList The list that should be searched
     * @param searchString The keywords
     * @return The SpellList in parameter, but with spells that does not apply with the search keyword removed.
     *
     * Uses the Search to search the spellList.
     */



    fun extractIndexListFromFile(context: Context, fileName: String): List<String> {
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            println("File does not exist.")
            return emptyList()
        }

        val jsonString = file.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        val resultsArray = jsonObject.getJSONArray("results")

        val indexList = mutableListOf<String>()
        for (i in 0 until resultsArray.length()) {
            val item = resultsArray.getJSONObject(i)
            indexList.add(item.getString("index"))
        }

        return indexList
    }

    fun isStringInList(list: List<String>, value: String): Boolean {
        return list.contains(value)
    }

    fun searchSpellName(spellList : SpellList, searchString : String) : SpellList {
        return search.searchSpellNames(spellList, searchString)
    }
    /**@author Tobias s224271
     * @param spellList SpellList to be filtered
     * @param filter Filter that specifies what should be returned
     * @return SpellList in parameter, but with spells that does not apply with filter removed.
     *
     */
    fun searchSpellListWithFilter(spellList : SpellList, filter: Filter) : SpellList {
        return search.searchSpellListWithFilter(spellList, filter)
    }

    fun getJsonIfStringInList(value: String, basePath: String): String? {

        val filePath = "$basePath/$value.json"
        val file = File(filePath)
        if (file.exists()) {
            return file.readText(Charsets.UTF_8)
        }
        else{
            println("FILE DOESNT EXIST BRO")
        }
        return null
    }

    private suspend fun getJSONFromList(list : List<String>) : List<String?>{
        return coroutineScope {
            list.map { spellName ->
                async {
                    getJson(spellName)
                }
            }.awaitAll()
        }
    }
    fun loadEntireSpellList(spellList : SpellList){
        val list = loadSpells(spellList.getIndexList())
        if(list.isNotEmpty()) spellList.setSpellInfoList(list)
    }

    fun loadNextFromSpellList(amount : Int, spellList: SpellList) : List<Spell_Info.SpellInfo?>? {
        val current = spellList.getLoaded() + 1;
        if (spellList.getIndexList().size >= current) return null
        val list: MutableList<String> = emptyList<String>().toMutableList()

        if (spellList.getIndexList().size > current + amount) {
            for (i in current..(current + amount)) {
                list.add(spellList.getIndexList()[i])
            }
            spellList.setLoaded(current + amount)
        } else {
            val to = spellList.getIndexList().size - 1
            for (i in current..to) {
                list.add(spellList.getIndexList()[i])
            }
            spellList.setLoaded(to)
        }
        return loadSpells(list)
    }
        suspend fun getJson(index: String): String? {

            val inList = isStringInList(localList, index)
            println(inList)
            println(localList)
            println(index)
            var json: String? = null

            if (inList) {
                json = getJsonIfStringInList(
                    index,
                    "/data/data/com.example.spellbook5eapplication/files/IndividualSpells"
                )
                println("NO API CALL")
            } else {
                json = api.getSpellFromApiWithRetry(index, 100)
                //Test for saving every spell
                println()
                if(json != null) saveJsonToFile(context, json, "IndividualSpells", index+".json")
            }

            return json
        }


        private fun loadSpells(indexes: List<String>): List<Spell_Info.SpellInfo> {
            var spellInfoJson: List<String?>
            runBlocking {
                spellInfoJson = getJSONFromList(indexes)
            }
            if (spellInfoJson.isEmpty()) return emptyList();
            val spellInfoList = mutableListOf<Spell_Info.SpellInfo>()
            for (spell in spellInfoJson) {
                if (spell != null) {
                    val spellInfo = spellInfoFromJSON(spell)
                    if (spellInfo != null) spellInfoList.add(spellInfo);
                }
            }
            return spellInfoList.toList();
        }

        fun spellInfoFromJSON(json: String): Spell_Info.SpellInfo? {
            val spellInfo = jsonToSpell.jsonToSpell(json)
            if (spellInfo != null) {

            }
            return spellInfo;
        }

    }
    /* The Four Previous Iterations of loadSpellList.
    Ended up using V3
    fun loadSpellListV1(spellList : SpellList){
        val spellInfoList = mutableSetOf<Spell_Info.SpellInfo>()
        for(spell in spellList.getSpellNamesList()){
            val loadedSpell = getSpellFromName(spell)
            if(loadedSpell != null) {
                try{
                    println(loadedSpell.name)
                    spellInfoList.add(loadedSpell)
                }catch (e: Exception){
                    println(e.message)
                }

            }
        }
        if(spellInfoList.isNotEmpty()) {
            spellList.setSpellInfoList(spellInfoList.toList())
        }
    }
    fun loadSpellListV2(spellList : SpellList){
        val spellInfoJson = mutableSetOf<String>()
        var amount = 0
        var percentage = 0
        println("Loading spells from api")
        runBlocking {
            for(spell in spellList.getSpellNamesList()){
                val json = api.getSpellFromApi(spell)
                if(json != null) spellInfoJson.add(json)
                if(amount++%6==0){
                    percentage =((amount/319.0)*100).roundToInt()
                    println("${percentage}%")
                }
            }
        }
        println("Converting JSON to data classes")
        if(spellInfoJson.isEmpty()) return
        val spellInfoList = mutableSetOf<Spell_Info.SpellInfo>()
        amount = 0
        for(spell in spellInfoJson){
            val spellInfo = jsonToSpell.jsonToSpell(spell)
            if(spellInfo != null) spellInfoList.add(spellInfo)

            if(amount++%6==0){
                percentage =((amount/319.0)*100).roundToInt()
                println("${percentage}%")
            }
        }
        if(spellInfoList.isNotEmpty()) {
            spellList.setSpellInfoList(spellInfoList.toList())
        }
    }
    fun loadSpellListV3(spellList : SpellList){
        var spellInfoJson : List<String?> = emptyList()
        println("Loading spells from api")
        runBlocking {
            var list = api.getSpellsFromApi(spellList.getSpellNamesList())
            if(list != null) spellInfoJson = list
        }
        var amount = 0
        var percentage = 0
        println("Converting JSON to data classes")
        if(spellInfoJson.isEmpty()) return
        val spellInfoList = mutableSetOf<Spell_Info.SpellInfo>()
        amount = 0
        for(spell in spellInfoJson){
            if(spell != null){
                val spellInfo = jsonToSpell.jsonToSpell(spell)
                if(spellInfo != null) spellInfoList.add(spellInfo)

                if(amount++%6==0){
                    percentage =((amount/319.0)*100).roundToInt()
                    println("${percentage}%")
                }
            }
        }
        if(spellInfoList.isNotEmpty()) {
            spellList.setSpellInfoList(spellInfoList.toList())
        }
    }
    fun loadSpellListV4(spellList : SpellList){
        val spellInfoJson = mutableListOf<List<String?>>()
        val splitter = ListSplitter()
        val namesInCollections = splitter.splitListIntoSize(spellList.getSpellNamesList(), 20)
        println("Loading spells from api")
        for(collection in namesInCollections){
            runBlocking {
                val list = api.getSpellsFromApi(collection)
                spellInfoJson.add(list)
            }
        }
        val allSpellJson = splitter.collect(spellInfoJson.toList())

        var amount = 0
        var percentage = 0
        println("Converting JSON to data classes")
        if(spellInfoJson.isEmpty()) return
        val spellInfoList = mutableSetOf<Spell_Info.SpellInfo>()
        amount = 0
        for(spell in allSpellJson){
            if(spell != null){
                val spellInfo = jsonToSpell.jsonToSpell(spell)
                if(spellInfo != null) spellInfoList.add(spellInfo)

                if(amount++%6==0){
                    percentage =((amount/319.0)*100).roundToInt()
                    println("${percentage}%")
                }
            }
        }
        if(spellInfoList.isNotEmpty()) {
            spellList.setSpellInfoList(spellInfoList.toList())
        }
    }
     */


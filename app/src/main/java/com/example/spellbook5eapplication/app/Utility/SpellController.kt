package com.example.spellbook5eapplication.app.Utility

import android.content.Context
import com.example.spellbook5eapplication.app.Model.API
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.JSON
import com.example.spellbook5eapplication.app.Model.JSON_to_Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File
import java.lang.ref.WeakReference
import org.json.JSONObject

object SpellController {

    private var context: WeakReference<Context>? = null

    fun setContext(appContext: Context) {
        context = WeakReference(appContext.applicationContext)
    }

    private fun getContext(): Context? {
        return context?.get()
    }


    private val api = API()
    private val jsonToSpell = JSON_to_Spell()
    private val search = Search()
    private var localList = emptyList<String>()

    fun getLocalList() : List<String>{
        if(localList.isEmpty()) localList = extractIndexListFromFile("LocalJSONData/spells.json")
        return localList
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
                val spellInfo: JSON? = getJson(spellName)
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
    suspend fun getAllSpellsList(): SpellList? {
        var list: SpellList? = null
        val appContext = getContext() // Retrieve the context or null if it's not available
        if (appContext == null) {
            println("Context is not available.")
            return null
        }



            try {
                val json = api.getListOfSpells()
                if (json != null) {
                    saveJsonToFile(json, "LocalJSONData", "spells.json")
                    list = jsonToSpell.jsonToSpellList(json)
                }
                else{
                    if(!getLocalList().isNullOrEmpty()){
                        val spellList = SpellList()
                        spellList.setIndexList(getLocalList())
                        list = spellList
                    }
                }
            } catch (e: Exception) {
                println("An error occurred: ${e.message}")
            }


        return list
    }

    /**
     * Saves a given JSON string to a specified file within a directory on the internal storage.
     *
     * This function creates a new directory (if it doesn't already exist) within the application's
     * internal storage directory, and then saves the JSON string into a file within that directory.
     * It utilizes the application's context to access the internal file system. This is particularly
     * useful for persisting data in a structured format (JSON) for later retrieval or processing.
     *
     * @param json The JSON string to be saved. This should be a well-formed JSON string.
     * @param directoryName The name of the directory where the file will be saved.
     *        The directory will be created in the app's internal storage if it doesn't exist.
     * @param fileName The name of the file to save the JSON string in. If the file already exists,
     *        it will be overwritten.
     * @throws Exception If there is an error during the file writing process, an exception is thrown
     *         with the relevant error message.
     * @author Kenneth Kaiser
     *
     */
    fun saveJsonToFile(json: String, directoryName: String, fileName: String) {
        val appContext = getContext()
        if (appContext != null) {
            try {
                // Create the directory in the internal storage
                val directory = File(appContext.filesDir, directoryName)
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
    }



    /**
     * Extracts and returns a list of spell indices from a specified JSON file stored in the application's
     * internal storage. This function is particularly useful for retrieving a collection of data items,
     * such as spell indices, from a locally stored JSON file.
     *
     * The function first checks for the existence of the file within the app's internal storage. If the
     * file is found, it reads the JSON content and attempts to parse it to extract the required data.
     * The JSON file is expected to contain either a "results" key with an array of objects each having
     * an "index" field, or a "spells" key with an array of strings.
     *
     * @param fileName The name of the file to read from. This file should be a JSON file and located in
     *        the app's internal storage directory.
     * @return A list of strings, each representing a spell index. If the file doesn't exist, or if the
     *         JSON structure is not as expected, the function returns an empty list.
     * @throws JSONException If there is an error parsing the JSON content of the file.
     * @throws IOException If there is an error reading the file.
     * @author Kenneth s221064
     */
    fun extractIndexListFromFile(fileName: String): List<String> {
        val context = getContext() ?: return emptyList() // Get the context or return if null

        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            println("File does not exist.")
            return emptyList()
        }

        val jsonString = file.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)

        // Check if the JSON object has the "results" key for spells
        if (jsonObject.has("results")) {
            val resultsArray = jsonObject.getJSONArray("results")
            val indexList = mutableListOf<String>()
            for (i in 0 until resultsArray.length()) {
                val item = resultsArray.getJSONObject(i)
                indexList.add(item.getString("index"))
            }
            return indexList
        }
        // Check if the JSON object has the "spells" key for spellbooks
        else if (jsonObject.has("spells")) {
            val spellsArray = jsonObject.getJSONArray("spells")
            val spellsList = mutableListOf<String>()
            for (i in 0 until spellsArray.length()) {
                spellsList.add(spellsArray.getString(i))
            }
            return spellsList
        } else {
            println("Unexpected JSON format.")
            return emptyList()
        }
    }

    /**@author Kenneth s221064
     * @param list The list of indexes stored on the local device
     * @param value The value we are checking if exists
     * @return boolean value if exists
     *
     * Checks if the value is inside of the list.
     */
    fun isStringInList(list: List<String>, value: String): Boolean {
        return list.contains(value)
    }
    /**@author Tobias s224271
     * @param spellList The list that should be searched
     * @param searchString The keywords
     * @return The SpellList in parameter, but with spells that does not apply with the search keyword removed.
     *
     * Uses the Search to search the spellList.
     */
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
    /**
     * Attempts to read and return the content of a JSON file located in a specified directory.
     * The function constructs the file path using the base path and the provided file name.
     * It then checks for the existence of the file at this path. If the file exists, the function
     * reads and returns its content as a string. Otherwise, it returns null.
     *
     * This function is useful for retrieving the contents of a JSON file when the file name is
     * dynamically determined based on some input value. It can be particularly helpful in scenarios
     * where the existence of the file needs to be checked before attempting to read its contents.
     *
     * @param value The name of the file (without the '.json' extension) to be searched for.
     * @param basePath The path of the directory where the file is expected to be located.
     * @return The content of the JSON file as a string if the file exists, or null if the file does not exist.
     * @throws IOException If there is an error in reading the file.
     * @author Kenneth s221064
     */
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

    /**@author Tobias s224271
     * @param list List of String indexes of spells.
     * @return List of JSON in the form of strings
     *
     * In a couroutineScope we ask for each spell and waits for all to be resieved before returning the list.
     *
     */
    private suspend fun getJSONFromList(list : List<String>) : List<JSON?>{
        return coroutineScope {
            list.map { spellName ->
                async {
                    getJson(spellName)
                }
            }.awaitAll()
        }
    }
    /**@author Tobias s224271
     * @param spellList The list that should be loaded
     *
     * Loads all the spells in the spelllist at once, this can create some load times, so for pagination use loadNextFromSpellList instead.
     */
    fun loadEntireSpellList(spellList : SpellList){
        val list = loadSpells(spellList.getIndexList())
        if(list.isNotEmpty()){
            spellList.setSpellInfoList(list)
            spellList.setLoaded(spellList.getIndexList().size)
        }
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
    /**@author Tobias s224271, Kenneth s221064
     * @param index the index of the spell to load a json for
     * @return The String format of JSON or null if not found in locale, database or api.
     *
     * Checks if the file is in the local storage of the device.
     * If it's there, it will be retrieved.
     * If it's not there we call the api for the json instead.
     * If nothing comes from the api either we return null.
     *
     * [FUTURE WORK]
     * We also add the database, as another thing to try to fetch the data from, if it's a homebrew spell.
     */
    suspend fun getJson(index: String): JSON? {

        val inList = isStringInList(getAllSpellsList()!!.getIndexList(), index)
        println(inList)
        println(getLocalList())
        println(index)
        var json : JSON
        var boolseye : Boolean = false
        if (inList) {
            val jsonString = getJsonIfStringInList(
                index,
                "/data/data/com.example.spellbook5eapplication/files/IndividualSpells"
            )
            if(jsonString != null){
                json = JSON(jsonString, "local")
                println("NO API CALL")
                return json
            }

        }
        else boolseye = true
        val jsonString = api.getSpellFromApiWithRetry(index, 100)
        //Test for saving every spell
        if(jsonString != null) {
            saveJsonToFile(jsonString, "IndividualSpells", index+".json")
            json = JSON(jsonString, "api")
            if(boolseye) json = JSON(jsonString, "debug")
            return json
        }
        else json = JSON("", "missing")
        return json
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
        var spellInfoJson: List<JSON?>
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

    /**@author Tobias s224271
     * @param json The String Json of a spell
     * @return The SpellInfo data class
     *
     * Converts using the jsonToSpell class
     */
    fun spellInfoFromJSON(json: JSON): Spell_Info.SpellInfo? {
        return jsonToSpell.jsonToSpell(json)
    }

    fun readJsonFromFile(directoryName: String, fileName: String, context: Context): String? {
        return try {
            val file = File(context.filesDir, "$directoryName/$fileName")
            if (file.exists()) {
                file.readText()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveHomeJSON(json: String, name: String) {
        saveJsonToFile(json, "HomeBrews", "HB_" + name + ".json")
    }

    fun deleteHomeBrew(name: String) {
        val appContext = SpellController.getContext()
        if (appContext != null) {
            try {
                val directory = File(appContext.filesDir, "HomeBrews")
                if (directory.exists()) {
                    val file = File(directory, "HB_$name.json")
                    if (file.exists()) {
                        file.delete()
                        println("Spell deleted: $name")
                    } else {
                        println("Spell not found: $name")
                    }
                } else {
                    println("HomeBrews directory does not exist")
                }
            } catch (e: Exception) {
                println("Failed to delete spell: ${e.message}")
            }
        }
    }

    fun retrieveHomeBrew(): SpellList? {
        val spells = SpellList()
        val directoryPath = "/data/user/0/com.example.spellbook5eapplication/files/HomeBrews/"
        val directory = File(directoryPath)
        var tempList = spells.getIndexList().toMutableList()
        var list = mutableListOf<Spell_Info.SpellInfo>()

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles { file -> file.isFile && file.extension == "json" }
            if (files != null) {
                for (file in files) {
                    try {

                        //NEW


                        val jsonString = file.readText()
                        // idk if this fix it
                        val json = JSON(jsonString, "Spell")
                        val spell = jsonToSpell.jsonToSpell(json)
                        if (spell != null) {
                            tempList.add(spell.index!!)
                            list.add(spell)
                        }
                        println("JSON read successfully from ${file.absolutePath}")
                    } catch (e: Exception) {
                        println("Error reading JSON file ${file.absolutePath}: ${e.message}")
                    }
                }
                spells.setIndexList(tempList.toList())
                spells.setSpellInfoList(list)
            }
        } else {
            println("Directory does not exist: $directoryPath")
        }

        return spells
    }


}
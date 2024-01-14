package com.example.spellbook5eapplication.app.Utility

import android.content.Context
import android.util.Log
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.lang.ref.WeakReference

object LocalDataLoader {

    private var baseDirectory : File? = null
    private var context: WeakReference<Context>? = null

    fun setContext(appContext: Context) {
        context = WeakReference(appContext.applicationContext)
        baseDirectory = context!!.get()?.filesDir
    }
    fun getIndexList(dataType : DataType) : List<String>{
        Log.d("1234567", dataType.value + " is datatype")
        if(dataType == DataType.HOMEBREW) return getIndexListFromDirectory("Homebrews")
        if(dataType == DataType.SPELLBOOK) return getIndexListFromDirectory("Spellbooks")
        var fileName = ""
        var directoryName = ""
        when(dataType){
            DataType.INDIVIDUAL -> {
                fileName = "individualSpells"
                directoryName = "LocalJSONData"
            }
            DataType.FAVOURITES ->  {
                fileName = "Favourites"
                directoryName = "Spellbooks"
            }

            else -> {return emptyList()}
        }
        return getIndexListFromFileName(directoryName = directoryName, name = fileName)
    }
    fun saveIndexList(dataType: DataType, list : List<String>){
        var directoryName = ""
        var fileName = ""
        when(dataType){
            DataType.INDIVIDUAL -> {
                directoryName = "LocalJSONData"
                fileName = "individualSpells"
            }
            DataType.HOMEBREW -> {
                directoryName = "LocalJSONData"
                fileName = "homebrewSpells"
            }
            DataType.FAVOURITES -> {
                directoryName = "Spellbooks"
                fileName = "Favourites"
            }
            else -> {return}
        }

        val indexListObject = Spellbook(fileName)
        list.forEach{
            indexListObject.addSpellToSpellbook(it)
        }
        val gson = Gson()
        val json = gson.toJson(indexListObject)
        saveJsonToFile(json, directoryName, fileName)
    }
    fun getSpellBookIndexList(name : String) : List<String> {
        return getIndexListFromFileName("Spellbooks", name)
    }
    fun saveJson(json : String, fileName: String, dataType: DataType) {
        Log.d("tobylog", dataType.value + "is datatype" + fileName + "is filename" + json + "is json")
        if(dataType == DataType.SPELLBOOK) saveJsonToFile(json, "Spellbooks", fileName)
        else
        {
            val directoryName = when(dataType){
                DataType.HOMEBREW -> "Homebrews"
                DataType.INDIVIDUAL -> "IndividualSpells"
                DataType.LOCAL -> "LocalJSONData"
                else -> {""}
            }
            if(directoryName == ""){
                println("No such directory")
                return
            }
            saveJsonToFile(json, directoryName, fileName)
        }
    }

    fun loadGraphQLQueryFromFile(): String {
        val localContext = context?.get()
        return if (localContext != null) {
            try {
                localContext.assets.open("spell_query.graphql").bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                Log.e("LocalDataLoader", "Error reading GraphQL query from file", ioException)
                ""
            }
        } else {
            Log.e("LocalDataLoader", "Context is null, cannot read GraphQL query from file")
            ""
        }
    }

    fun updateIndividualSpellList(spellName : String){
        println("Inserting $spellName")
        val indexList = getIndexList(DataType.INDIVIDUAL).toMutableList()
        if(indexList.contains(spellName)) return
        indexList.add(spellName)
        println(indexList.toString())
        saveIndexList(DataType.INDIVIDUAL, indexList.toList())
    }

    fun getJson(fileName: String, dataType: DataType) : String?{
        val directoryName = when(dataType){
            DataType.HOMEBREW -> "Homebrews"
            DataType.INDIVIDUAL -> "IndividualSpells"
            DataType.SPELLBOOK -> "Spellbooks"
            else -> {""}
        }
        if(directoryName == ""){
            println("No such directory")
            return null
        }
        return getLocalJson("$directoryName/$fileName")
    }
    private fun saveJsonToFile(json: String, directoryName: String, fileName: String) {
        try {
            val directory = File(baseDirectory, directoryName)
            if (!directory.exists()) {
                directory.mkdirs() // Make the directory if it does not exist
            }
            val file = File(directory, "$fileName.json")
            Log.d("deez12312", file.absolutePath)
            file.writeText(json)
        } catch (e: Exception) {
            println("Failed to save JSON to file: ${e.message}")
        }
    }
    private fun createEmptySpellbook(directoryName: String, fileName: String){
        println("Created file $fileName")
        val file = File(baseDirectory, "$directoryName/$fileName.json")
        val emptySpellbook = Spellbook(fileName)
        val gson = Gson()
        val json = gson.toJson(emptySpellbook)
        file.writeText(json)
    }
    private fun getIndexListFromFileName(directoryName: String, name: String) : List<String>{

        val directoryExists = File(baseDirectory, directoryName)
        if(!directoryExists.exists()) directoryExists.mkdirs()

        val file = File(baseDirectory, "$directoryName/$name.json")
        if (!file.exists()) {
            println("File does not exist: $name")
            createEmptySpellbook(directoryName, name)
            return emptyList()
        } //No such file
        val jsonString = file.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)

        val spellsArray : JSONArray = try {
            jsonObject.getJSONArray("spells")
        }
        catch (e : Exception)
        {
            println("no spells variable in json $name")
            null
        } ?: return emptyList()

        val spellsList = mutableListOf<String>()
        for (i in 0 until spellsArray.length()) {
            spellsList.add(spellsArray.getString(i))
        }

        Log.d("deez", spellsList.toString())

        return spellsList
    }
    private fun getIndexListFromDirectory(directoryName: String) : List<String> {
        val targetDirectory = File(baseDirectory, directoryName)

        if(!targetDirectory.exists()) targetDirectory.mkdirs()

        val list = if (targetDirectory.exists() && targetDirectory.isDirectory) {
            targetDirectory.listFiles()?.map { it.name } ?: emptyList()
        } else {
            return emptyList()
        }
        val modifiedList = list.map { it.removeSuffix(".json") }

        return modifiedList
    }

    private fun getLocalJson(fileName: String) : String? {
        val file = File(baseDirectory, "$fileName.json")
        if (file.exists()) {
            return file.readText(Charsets.UTF_8)
        }
        return null //Spell not found
    }
    fun deleteFile(fileName: String, dataType : DataType): Boolean {
        val directoryName = when(dataType){
            DataType.HOMEBREW -> "Homebrews"
            DataType.INDIVIDUAL -> "IndividualSpells"
            DataType.SPELLBOOK -> "Spellbooks"
            else -> {""}
        }
        try {
            // Locate the file in the specified directory
            val file = File(baseDirectory, "$directoryName/$fileName"+".json")

            if (file.exists()) {
                // Attempt to delete the file and return the result
                return file.delete().also { isDeleted ->
                    if (isDeleted) {
                        println("File deleted successfully: ${file.absolutePath}")
                    } else {
                        println("Failed to delete the file.")
                    }
                }
            } else {
                println("File does not exist: ${file.absolutePath}")
            }
        } catch (e: Exception) {
            println("Error occurred while deleting the file: ${e.message}")
            return false
        }
        return false
    }
    enum class DataType(val value: String) {
        INDIVIDUAL("IndividualSpells"),
        SPELLBOOK("Spellbooks"),
        FAVOURITES("Favourites"),
        HOMEBREW("Homebrews"),
        LOCAL("LocalJSONData"),
    }

}
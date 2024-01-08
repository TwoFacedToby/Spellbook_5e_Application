package com.example.spellbook5eapplication.app.Utility

import android.content.Context
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.lang.ref.WeakReference

object LocalDataLoader {

    private var baseDirectory : File? = null
    private var context: WeakReference<Context>? = null

    fun setContext(appContext: Context) {
        context = WeakReference(appContext.applicationContext)
        baseDirectory = context!!.get()?.filesDir
    }
    fun getIndexList(dataType : DataType) : List<String>{
        if(dataType == DataType.HOMEBREW) return getIndexListFromDirectory("Homebrews")
        if(dataType == DataType.SPELLBOOK) return getIndexListFromDirectory("Spellbooks")
        val fileName = when(dataType){
            DataType.INDIVIDUAL -> "LocalJSONData/individualSpells.json"
            DataType.FAVOURITES ->  "Spellbooks/Favourites.json"
            else -> {""}
        }
        if(fileName == ""){
            println("No file found")
            return emptyList()
        } //No such filename
        return getIndexListFromFileName(fileName)
    }
    fun saveIndexList(dataType: DataType, list : List<String>){
        val fileName = when(dataType){
            DataType.INDIVIDUAL -> "individualSpells"
            DataType.HOMEBREW -> "homebrewSpells"
            DataType.FAVOURITES ->  "Favourites"
            else -> {""}
        }
        if(fileName == ""){
            println("No file found")
            return
        } //No such filename
        //TODO - Write saving code

        val indexListObject = Spellbook(fileName)
        list.forEach{
            indexListObject.addSpellToSpellbook(it)
        }
        val gson = Gson()
        val json = gson.toJson(indexListObject)
        saveJson(json, fileName, dataType)
    }
    fun getSpellBookIndexList(name : String) : List<String> {
        return getIndexListFromFileName("Spellbooks/$name")
    }
    fun saveJson(json : String, fileName: String, dataType: DataType) {
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
            file.writeText(json)
        } catch (e: Exception) {
            println("Failed to save JSON to file: ${e.message}")
        }
    }
    private fun getIndexListFromFileName(name : String) : List<String>{
        val file = File(baseDirectory, "$name.json")
        if (!file.exists()) {
            println("File does not exist.")
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
        return spellsList
    }
    private fun getIndexListFromDirectory(directoryName: String) : List<String> {
        val targetDirectory = File(baseDirectory, directoryName)

        return if (targetDirectory.exists() && targetDirectory.isDirectory) {
            targetDirectory.listFiles()?.map { it.name } ?: emptyList()
        } else {
            emptyList() // Or throw an exception if the directory does not exist
        }
    }
    private fun getLocalJson(fileName: String) : String? {
        val file = File(baseDirectory, fileName)
        if (file.exists()) {
            return file.readText(Charsets.UTF_8)
        }
        return null
    }
    fun deleteFile(fileName: String, dataType : DataType): Boolean {
        val directoryName = when(dataType){
            DataType.HOMEBREW -> "Homebrews"
            DataType.INDIVIDUAL -> "IndividualSpells"
            else -> {""}
        }
        try {
            // Locate the file in the specified directory
            val file = File(baseDirectory, "$directoryName/$fileName")

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
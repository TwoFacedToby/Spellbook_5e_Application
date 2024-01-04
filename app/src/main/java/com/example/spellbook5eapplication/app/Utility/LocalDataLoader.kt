package com.example.spellbook5eapplication.app.Utility

import android.content.Context
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
    fun getIndexList(dataType : LocalData) : List<String>{
        val fileName = when(dataType){
            LocalData.INDIVIDUAL -> "LocalJSONData/individualSpells.json"
            LocalData.HOMEBREW -> "LocalJSONData/homebrewSpells.json"
            LocalData.FAVOURITES ->  "LocalJSONData/favouriteSpells.json"
            else -> {""}
        }
        if(fileName == ""){
            println("No file found")
            return emptyList()
        } //No such filename
        return getIndexListFromFileName(fileName)
    }
    fun getSpellBookIndexList(name : String) : List<String> {
        return getIndexListFromFileName("Spellbooks/$name")
    }
    fun saveJson(json : String, fileName: String, dataType: LocalData) {
        val directoryName = when(dataType){
            LocalData.HOMEBREW -> "Homebrews"
            LocalData.INDIVIDUAL -> "IndividualSpells"
            else -> {""}
        }
        if(directoryName == ""){
            println("No such directory")
            return
        }
        saveJsonToFile(json, directoryName, fileName)
    }
    fun getJson(fileName: String, dataType: LocalData) : String?{
        val directoryName = when(dataType){
            LocalData.HOMEBREW -> "Homebrews"
            LocalData.INDIVIDUAL -> "IndividualSpells"
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
            val file = File(directory, fileName)
            file.writeText(json)
        } catch (e: Exception) {
            println("Failed to save JSON to file: ${e.message}")
        }
    }
    private fun getIndexListFromFileName(name : String) : List<String>{
        val file = File(baseDirectory, name)
        if (!file.exists()) {
            println("File does not exist.")
            return emptyList()
        } //No such file
        val jsonString = file.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)

        val spellsArray = jsonObject.getJSONArray("spells")
        val spellsList = mutableListOf<String>()
        for (i in 0 until spellsArray.length()) {
            spellsList.add(spellsArray.getString(i))
        }
        return spellsList
    }
    private fun getLocalJson(fileName: String) : String? {
        val file = File(baseDirectory, fileName)
        if (file.exists()) {
            return file.readText(Charsets.UTF_8)
        }
        else{
            println("FILE DOESNT EXIST BRO")
        }
        return null
    }

    /**
     * Deletes a specified file from a given directory.
     *
     * This function looks for the file in the specified directory within the application's internal storage.
     * If the file is found, it is deleted. The function provides feedback via the console about the
     * success or failure of the deletion process.
     *
     * @param directoryName The name of the directory where the file is located.
     * @param fileName The name of the file to be deleted.
     * @author Kenneth Kaiser
     */
    private fun deleteFileFromDirectory(directoryName: String, fileName: String): Boolean {
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
    enum class LocalData(val value: String) {
        INDIVIDUAL("IndividualSpells"),
        SPELLBOOK("Spellbooks"),
        FAVOURITES("Favourites"), //TODO - insert homebrew here
        HOMEBREW("Homebrews"), //TODO - insert homebrew here
    }

}
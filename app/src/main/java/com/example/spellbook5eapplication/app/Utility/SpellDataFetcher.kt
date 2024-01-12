package com.example.spellbook5eapplication.app.Utility

import android.util.Log
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.viewmodel.SpellsViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object SpellDataFetcher {

    private val SpellList = SpellList()

    private val spellInfoMap = HashMap<String, Spell.SpellInfo>()

    suspend fun localOrAPI(index: String): Spell.SpellInfo? {
        if(spellInfoMap.containsKey(index)) {
            return spellInfoMap[index]
        }

        // Function to parse JSON and extract 'spell' from 'data'
        fun parseSpellJson(json: String): Spell.SpellInfo? {
            val jsonObject = Gson().fromJson(json, JsonObject::class.java)
            val spellJson = jsonObject.getAsJsonObject("data")?.getAsJsonObject("spell")
            return Gson().fromJson(spellJson, Spell.SpellInfo::class.java)
        }

        // Check if the spell info already exists in the local data
        LocalDataLoader.getJson(index, LocalDataLoader.DataType.INDIVIDUAL)?.let { json ->
            val spellInfo = parseSpellJson(json)
            Log.d("SpellDataFetcher", "Fetched from local storage: $index - ${spellInfo.toString()}")
            spellInfo?.let { addSpellInfo(it) }
            return spellInfo
        }

        // Check if the spell info exists in homebrew data
        LocalDataLoader.getJson(index, LocalDataLoader.DataType.HOMEBREW)?.let { json ->
            val spellInfo = parseSpellJson(json)

            Log.d("SpellDataFetcher", "Fetched from homebrew: $index - $json")
            Log.d("SpellDataFetcher", "Fetched from homebrew: $index - ${spellInfo.toString()}")
            spellInfo?.let { addSpellInfo(it) }
            return spellInfo
        }

        // If not found in local or homebrew, fetch from API
        return fetchFromAPI(index)?.also { spellInfo ->
            addSpellInfo(spellInfo)
            Log.d("SpellDataFetcher", "Fetched from API: $index - ${spellInfo.toString()}")
        }
    }



    suspend fun fetchFromAPI(index: String): Spell.SpellInfo? {
        return withContext(Dispatchers.IO) {
            try {
                // Fetch the JSON string of the spell details from the API
                val jsonResult = SpellsViewModel.getSpellDetails(index)
                if (jsonResult.isNotEmpty()) {
                    // Parse the JSON string to Spell.SpellInfo object
                    val gson = Gson()
                    val jsonObject = gson.fromJson(jsonResult, JsonObject::class.java)
                    val spellJson = jsonObject.getAsJsonObject("data")?.getAsJsonObject("spell")

                    // Save the fetched JSON locally for future use
                    LocalDataLoader.saveJson(jsonResult, index, LocalDataLoader.DataType.INDIVIDUAL)
                    LocalDataLoader.updateIndividualSpellList(index)

                    gson.fromJson(spellJson, Spell.SpellInfo::class.java)
                } else {
                    // Return null if no data is fetched
                    Log.e("SpellDataFetcher", "No data fetched for index $index")
                    null
                }
            } catch (e: Exception) {
                // Log the error and return null in case of an exception
                Log.e("SpellDataFetcher", "Error fetching spell details for index $index", e)
                null
            }
        }
    }

    fun addSpellInfo(spellInfo: Spell.SpellInfo) {
        spellInfo.index?.let { index ->
            spellInfoMap.putIfAbsent(index, spellInfo)
        }
    }

    fun getSpellInfo(index: String?): Spell.SpellInfo? {
        return spellInfoMap[index]
    }

    fun hasSpellInfo(index: String?): Boolean {
        return spellInfoMap.containsKey(index)
    }

    fun loadSpellInfos(spellInfos: List<Spell.SpellInfo?>) {
        for (spellInfo in spellInfos) {
            addSpellInfo(spellInfo!!)
        }
    }

    suspend fun sneakyQuickLoader(){
        val sneakyList = SpellsViewModel.fetchAllSpellNames()
        sneakyList.getIndexList().forEach {
            localOrAPI(it)
        }
    }


}
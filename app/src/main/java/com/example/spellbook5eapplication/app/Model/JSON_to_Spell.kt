package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Model.Data_Model.JSON
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class JSON_to_Spell {
    fun jsonToSpell(json : String) : Spell.SpellInfo? {
        val gson = Gson()
        val spell = gson.fromJson(json, Spell.SpellInfo::class.java)
        if(spell.desc?.isEmpty() != false) return null
        spell.url = json
        return spell
    }
    fun jsonToSpellList(spellsResponse: Spell.SpellsResponseOverview): SpellList {
        val spells = SpellList()
        spells.setIndexList(spellsResponse.spells.mapNotNull { it.index }) // or it.index if you need indexes
        return spells
    }

    private fun extractIndexesFromJson(jsonString: String): List<String> {
        val jsonParser = JsonParser()
        val jsonElement = jsonParser.parse(jsonString)
        if (jsonElement is JsonObject) {
            val resultsArray = jsonElement.getAsJsonArray("results")
            if (resultsArray != null) {
                val indexes = mutableListOf<String>()
                for (element in resultsArray) {
                    if (element is JsonObject) {
                        // Check if the JSON object has an "index" key
                        val index = element.get("index")
                        if (index != null && index.isJsonPrimitive) {
                            indexes.add(index.asString)
                        }
                    }
                }
                return indexes
            }
        }
        return emptyList()
    }

    fun spellToJson(spell : Spell) : String? {
        val gson = Gson()
        val json = gson.toJson(spell)
        return json
    }

}
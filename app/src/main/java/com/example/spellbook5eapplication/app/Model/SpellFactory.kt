package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

class SpellFactory {
    fun createSpellsFromJsons(jsons : List<String>) : List<Spell> {
        val spells : MutableList<Spell> = mutableListOf()
        for (json in jsons) {
            spells.add(createSpellFromJson(json))
        }
        return spells
    }
    fun createSpellFromJson(jsonString: String): Spell {
        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)

        val spell = Spell()
        spell.json = jsonString

        //TODO - Add all json variables as variables for spell.
        spell.index = jsonObject.get("index").asString
        spell.name = jsonObject.get("name").asString
        spell.desc = gson.fromJson(jsonObject.get("desc"), object : TypeToken<List<String>>() {}.type)
        spell.atHigherLevel = gson.fromJson(jsonObject.get("higher_level"), object : TypeToken<List<String>>() {}.type)
        spell.range = jsonObject.get("range").asString
        spell.components = gson.fromJson(jsonObject.get("components"), object : TypeToken<List<String>>() {}.type)
        spell.materials = jsonObject.get("material").asString
        spell.ritual = jsonObject.get("ritual").asBoolean
        spell.duration = jsonObject.get("duration").asString
        spell.concentration = jsonObject.get("concentration").asBoolean
        spell.castingTime = jsonObject.get("casting_time").asString
        spell.level = jsonObject.get("level").asInt
        spell.attackType = jsonObject.getAsJsonObject("damage").get("attack_type").asString
        spell.url = jsonObject.get("url").asString

        // Extracting just the names from nested objects and arrays
        spell.school = jsonObject.getAsJsonObject("school").get("name").asString
        spell.classes = extractNames(jsonObject.getAsJsonArray("classes"))


        return spell
    }
    private fun extractNames(jsonArray: JsonArray): List<String> {
        return jsonArray.map { it.asJsonObject.get("name").asString }
    }
}

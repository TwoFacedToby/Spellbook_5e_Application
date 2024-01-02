package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.google.gson.Gson
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
        spell.index = jsonObject.get("json_index").asString //String example
        spell.name = jsonObject.get("json_name").asString

        val descListType = object : TypeToken<List<String>>() {}.type //Lists of String example
        spell.desc = gson.fromJson(jsonObject.get("json_descList"), descListType)

        spell.ritual = jsonObject.get("json_ritual").asBoolean //Boolean example

        spell.level = jsonObject.get("json_level").asInt //Integer example

        return spell
    }
}

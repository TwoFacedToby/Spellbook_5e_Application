package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken

class SpellFactory {
    fun createSpellsFromJsons(jsons: List<String>): List<Spell.SpellInfo> {
        val spells: MutableList<Spell.SpellInfo> = mutableListOf()
        for (json in jsons) {
            spells.add(createSpellFromJson(json))
        }
        return spells
    }

        fun createSpellFromJson(jsonString: String): Spell.SpellInfo {
            val gson = Gson()
            val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)

            val spell = Spell.SpellInfo(
                json = jsonString,
                index = jsonObject.get("index")?.asString,
                name = jsonObject.get("name")?.asString,
                desc = jsonObject.get("desc")
                    ?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) },
                atHigherLevel = jsonObject.get("higher_level")
                    ?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) },
                range = jsonObject.get("range")?.asString,
                components = jsonObject.get("components")
                    ?.let { gson.fromJson(it, object : TypeToken<List<String>>() {}.type) },
                materials = jsonObject.get("material")?.asString,
                ritual = jsonObject.get("ritual")?.asBoolean,
                duration = jsonObject.get("duration")?.asString,
                concentration = jsonObject.get("concentration")?.asBoolean,
                casting_time = jsonObject.get("casting_time")?.asString,
                level = jsonObject.get("level")?.asInt,
                school = jsonObject.get("school")
                    ?.let { gson.fromJson(it, Spell.SpellSchool::class.java) },
                classes = jsonObject.get("classes")?.let {
                    gson.fromJson(
                        it,
                        object : TypeToken<List<Spell.SpellClasses>>() {}.type
                    )
                },
                url = jsonObject.get("url")?.asString,
                attackType = jsonObject.getAsJsonObject("damage")?.get("attack_type")?.asString,
                // New fields
                damage = jsonObject.get("damage")
                    ?.let { gson.fromJson(it, Spell.SpellDamage::class.java) },
                dc = jsonObject.get("dc")?.asString,
                homebrew = jsonObject.get("homebrew")?.asBoolean
            )

            return spell
        }


    private fun extractNames(jsonArray: JsonArray): List<String> {
        return jsonArray.map { it.asJsonObject.get("name").asString }
    }
}

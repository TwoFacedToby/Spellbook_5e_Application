package com.example.spellbook5eapplication.app.Utility

import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.google.gson.Gson
import java.io.File

class HomeBrewManager {

    data class DamageType(val index: String, val name: String, val url: String)
    data class Damage(val damage_type: DamageType, val damage_at_slot_level: Map<String, String>)
    data class School(val index: String, val name: String, val url: String)
    data class ClassInfo(val index: String, val name: String, val url: String)
    data class Subclass(val index: String, val name: String, val url: String)
    data class Spell(
        val index: String,
        val name: String,
        val desc: List<String>,
        val higher_level: List<String>,
        val range: String,
        val components: List<String>,
        val material: String,
        val ritual: Boolean,
        val duration: String,
        val concentration: Boolean,
        val casting_time: String,
        val level: Int,
        val attack_type: String,
        val damage: Damage,
        val school: School,
        val classes: List<ClassInfo>,
        val subclasses: List<Subclass>,
        val url: String
    )

    fun createSpellJson(name: String, description: String, range: String, components: List<String>, ritual: Boolean, concentration: Boolean, duration: String,
                        casting_time: String, level: Int): String {
        val gson = Gson()

        val spell = Spell(
            name.lowercase(),
            name,
            listOf(
                description
            ),
            listOf(""),
            range,
            components,
            "",
            ritual,
            duration,
            concentration,
            casting_time,
            level,
            //The rest of the aspect are all hardcoded for now
            "ranged",
            Damage(
                DamageType("acid", "Acid", "/api/damage-types/acid"),
                mapOf("2" to "4d4", "3" to "5d4", "4" to "6d4", "5" to "7d4", "6" to "8d4", "7" to "9d4", "8" to "10d4", "9" to "11d4")
            ),
            School("evocation", "Evocation", "/api/magic-schools/evocation"),
            listOf(ClassInfo("wizard", "Wizard", "/api/classes/wizard")),
            listOf(
                Subclass("lore", "Lore", "/api/subclasses/lore"),
                Subclass("land", "Land", "/api/subclasses/land")
            ),
            "/api/spells/acid-arrow"
        )
        addHomebrewSpell(gson.toJson(spell), name)
        return gson.toJson(spell)
    }
    private fun addHomebrewSpell(json : String, name : String){
        LocalDataLoader.saveJson(json, name, LocalDataLoader.DataType.HOMEBREW)
        val list = LocalDataLoader.getIndexList(LocalDataLoader.DataType.HOMEBREW).toMutableList()
        list.add(name)
        LocalDataLoader.saveIndexList(LocalDataLoader.DataType.HOMEBREW, list.toList())
    }
    fun retrieveHomeBrew(): List<String> {
        return LocalDataLoader.getIndexList(LocalDataLoader.DataType.HOMEBREW)
    }
}

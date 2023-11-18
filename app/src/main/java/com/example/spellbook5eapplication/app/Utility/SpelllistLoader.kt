package com.example.spellbook5eapplication.app.Utility
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.google.gson.Gson
import java.io.File


object SpelllistLoader {

    /**
     * Load a spellbook from a JSON file and convert it to a SpellList.
     * @param filePath The path to the spellbook JSON file.
     * @param spellController The SpellController instance to use for loading spell details.
     * @return A SpellList containing the spells from the spellbook.
     */
    fun loadSpellbookAsSpellList(filePath: String, spellController: SpellController): SpellList {
        // Read the spellbook JSON
        val json = File(filePath).readText()
        val spellbook = Gson().fromJson(json, Spellbook::class.java)

        // Get detailed spell info for each spell name
        val spellInfoList = spellbook.spells.mapNotNull { spellName ->
            spellController.getSpellFromName(spellName)
        }

        // Create and populate the SpellList
        val spellList = SpellList()
        spellList.setIndexList(spellbook.spells) // Assuming the spells in Spellbook are just names
        spellList.setSpellInfoList(spellInfoList)

        return spellList
    }

    /**
     * Load the "Favourites" spellbook from its JSON file and convert it to a SpellList.
     * @param spellController The SpellController instance to use for loading spell details.
     * @return A SpellList containing the favourite spells.
     */
    fun loadFavouritesAsSpellList(spellController: SpellController): SpellList {
        val filePath =
            "/data/data/com.example.spellbook5eapplication/files/Spellbooks/Favourites.json"
        val file = File(filePath)

        if (file.exists()) {
            val json = file.readText()

            if (json.isNotEmpty()) {
                val favourites = Gson().fromJson(json, Spellbook::class.java)

                if (favourites != null) {
                    val spellInfoList = favourites.spells.mapNotNull { spellName ->
                        spellController.getSpellFromName(spellName)
                    }

                    val spellList = SpellList()
                    spellList.setIndexList(favourites.spells)
                    spellList.setSpellInfoList(spellInfoList)
                    return spellList
                } else {
                    println("Failed to deserialize JSON into Spellbook: $json")
                }
            } else {
                println("Favourites JSON file is empty.")
            }
        } else {
            println("Favourites JSON file does not exist.")
        }

        return SpellList() // Return an empty SpellList if there were any issues
    }
}

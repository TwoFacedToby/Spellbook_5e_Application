package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Utility.SpellController
import com.google.gson.Gson

class Favourites {
    var favouriteList = mutableListOf<String>()

    fun addFavourite(spellname : String) {
        favouriteList.add(spellname)
    }

    fun removeFavourite(spellname : String) {
        val wasRemoved = favouriteList.remove(spellname)
        if (wasRemoved) {
            println("$spellname removed from favourites")
        } else {
            println("$spellname not found in favourites")
        }
    }

    fun saveSpellbookToFile(spellBookName: String) {
        favouriteList?.let {
            val gson = Gson()
            val favouritesListGson = gson.toJson(it)

            SpellController.saveJsonToFile(favouritesListGson, "Favourites", spellBookName + ".json")
        }
    }
}

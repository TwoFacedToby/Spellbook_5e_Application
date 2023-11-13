package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Utility.SpellController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Favourites {
    var favouriteList = mutableListOf<String>()

    fun addFavourite(spellname : String) {
        val wasAdded = favouriteList.add(spellname)
        if(wasAdded){
            println("$spellname was added to favourite")
        } else {
            println("Unknown error encountering, while trying to add spellname to favouriteList object")

        }
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

    fun loadSpellbookFromFile(spellBookName: String): Boolean {
        val fileContent = SpellController.readJsonFromFile("Favourites", spellBookName + ".json")
        fileContent?.let {
            val gson = Gson()
            val type = object : TypeToken<MutableList<String>>() {}.type
            favouriteList = gson.fromJson(it, type)
            return true
        }
        return false
    }
}

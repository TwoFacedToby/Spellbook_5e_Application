    package com.example.spellbook5eapplication.app.Model

    import com.example.spellbook5eapplication.app.Utility.SpellController
    import com.example.spellbook5eapplication.app.Utility.SpellbookManager
    import com.google.gson.Gson
    import com.google.gson.reflect.TypeToken
    import org.json.JSONObject
    import java.io.File


    class Favourites {
        val favouritesSpellbook = SpellController.extractIndexListFromFile("Spellbooks/Favourites.json").toMutableList()

        fun addFavourite(spellname : String) {
            favouritesSpellbook.add(spellname)
            }

        fun removeFavourite(spellname : String) {
            val wasRemoved = favouritesSpellbook.remove(spellname)
            if (wasRemoved) {
                println("$spellname removed from favourites")
            } else {
                println("$spellname not found in favourites")
            }
        }



        fun saveFavouritesAsSpellbook(favouriteList: List<String>){
            SpellbookManager().saveSpellbookToFile("Favourites")
        }
    }
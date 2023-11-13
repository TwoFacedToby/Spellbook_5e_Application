package com.example.spellbook5eapplication.app.Model

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
}

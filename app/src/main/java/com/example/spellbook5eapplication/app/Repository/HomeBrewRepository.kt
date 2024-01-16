package com.example.spellbook5eapplication.app.Repository

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeBrewRepository {

    private val database = Firebase.database

    fun saveHomeBrewSpell(userId: String, homebrewName: String, data: String) {
        if (userId == "") {
            Log.e("Error", "User is not logged in")
            return
        }
        val myRef = database.getReference("User/$userId/$homebrewName")
        myRef.setValue(data)
    }

    fun loadHomeBrewSpell(userId: String, homebrewName: String) {
        if (userId == "") {
            Log.e("Error", "User is not logged in")
            return
        }
        val myRef = database.getReference("User/$userId/$homebrewName")
        myRef.get()
    }

    fun deleteHomeBrewSpell(userId: String, homebrewName: String) {
        if (userId == "") {
            Log.e("Error", "User is not logged in")
            return
        }
        val myRef = database.getReference("User/$userId/$homebrewName")
        myRef.removeValue()
    }

    fun editHomeBrewSpell(userId: String, homebrewName: String, data: String) {
        if (userId == "") {
            Log.e("Error", "User is not logged in")
            return
        }
        val myRef = database.getReference("User/$userId/$homebrewName")
        myRef.setValue(data)
    }
}
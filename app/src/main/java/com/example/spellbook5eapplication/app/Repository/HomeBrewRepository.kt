package com.example.spellbook5eapplication.app.Repository

import android.util.Log
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Utility.GlobalLogInState.userId
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

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

    fun loadAllHomeBrewSpellsFromFirebase(userId: String, onDataReceived: (Map<String, String?>) -> Unit) {
        if (userId == "") {
            Log.e("Error", "User is not logged in")
            return
        }
        val myRef = database.getReference("User/$userId")
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataMap = mutableMapOf<String, String?>()
                snapshot.children.forEach { childSnapshot ->
                    val homebrewName = childSnapshot.key
                    val data = childSnapshot.getValue<String>()
                    if (homebrewName != null) {
                        dataMap[homebrewName] = data
                    }
                }
                onDataReceived(dataMap)
                Log.d("TESTfourhundredten", "onDataChange: $dataMap")
                importSpellsFromDatabase()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeBrewRepository", "Failed to read value.", error.toException())
                onDataReceived(emptyMap())
            }
        })
    }

    fun importSpellsFromDatabase() {
        val databaseReference = FirebaseDatabase.getInstance().reference.child("User/$userId")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (spellSnapshot in snapshot.children) {
                    val index = spellSnapshot.key // This is the index of the spell
                    val json = spellSnapshot.getValue<String>() // The JSON string of the spell

                    if (index != null && json != null) {
                        Log.d("JsonDataFromFirebase", json)
                        LocalDataLoader.saveJson(json, index, LocalDataLoader.DataType.HOMEBREW)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("importSpells", "loadSpells:onCancelled", databaseError.toException())
            }
        })
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
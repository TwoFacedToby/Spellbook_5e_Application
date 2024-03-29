package com.example.spellbook5eapplication.app.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.gson.Gson

class CreateSpellbookViewModel : ViewModel() {

    var spellbookName by mutableStateOf("")
        private set

    fun updateSpellbookName(newName: String) {
        spellbookName = newName
    }

    var spellbookDescription by mutableStateOf("")
        private set

    fun updateSpellbookDescription(newDescription: String) {
        spellbookDescription = newDescription
    }

    var selectedImageIdentifier by mutableStateOf("spellbook_brown")
        private set

    fun updateSelectedImageIdentifier(newIdentifier: String) {
        selectedImageIdentifier = newIdentifier
    }

    fun saveSpellbook() {
        val spellbook = Spellbook(spellbookName, selectedImageIdentifier, spellbookDescription)

        SpellbookManager.addSpellbook(spellbook)

        val gson = Gson()
        val jsonSpellbook = gson.toJson(spellbook)





        LocalDataLoader.saveJson(
            jsonSpellbook,
            spellbookName.lowercase(),
            LocalDataLoader.DataType.SPELLBOOK
        )
        println(jsonSpellbook)
    }

}
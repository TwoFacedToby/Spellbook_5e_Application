package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

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

}
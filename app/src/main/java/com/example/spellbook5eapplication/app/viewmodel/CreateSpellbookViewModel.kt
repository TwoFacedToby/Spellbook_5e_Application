package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class CreateSpellbookViewModel {

    var spellbookName by mutableStateOf("")
        private set

    fun updateSpellbookName(newName: String) {
        spellbookName = newName
    }

}
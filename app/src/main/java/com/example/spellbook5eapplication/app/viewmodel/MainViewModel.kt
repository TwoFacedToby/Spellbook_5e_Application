package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var currentTitle by mutableStateOf<String?>(null)
        private set

    fun updateTitle(newTitle: String) {
        currentTitle = newTitle
    }
}
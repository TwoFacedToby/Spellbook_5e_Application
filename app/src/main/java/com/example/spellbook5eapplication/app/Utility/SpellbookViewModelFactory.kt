package com.example.spellbook5eapplication.app.Utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SpellbookViewModelFactory(
    private val spellController: SpellController,
    private val spellListLoader: SpelllistLoader
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpellbookViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SpellbookViewModel(spellController, spellListLoader) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
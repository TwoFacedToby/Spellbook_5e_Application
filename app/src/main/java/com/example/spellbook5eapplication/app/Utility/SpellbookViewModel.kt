package com.example.spellbook5eapplication.app.Utility

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Spellbook
import kotlinx.coroutines.launch

class SpellbookViewModel(
    private val spellController: SpellController,
    private val spellListLoader: SpelllistLoader
) : ViewModel() {
    // State for all spellbooks
    val spellbooks = mutableStateListOf<Spellbook>()

    // State for selected spellbook
    private val _selectedSpellbook = mutableStateOf<Spellbook?>(null)
    val selectedSpellbook: State<Spellbook?> = _selectedSpellbook

    // State for the spell list
    private val _spellList = mutableStateOf<SpellList?>(null)
    val spellList: State<SpellList?> = _spellList

    init {
        spellbooks.addAll(SpellbookManager.getAllSpellbooks())
    }

    fun selectSpellbook(spellbook: Spellbook) {
        // Update the mutable state property
        _selectedSpellbook.value = spellbook
        loadSpellListForSelectedSpellbook(spellbook)
    }

    private fun loadSpellListForSelectedSpellbook(spellbook: Spellbook) {
        viewModelScope.launch {
            val temporarySpelllist = spellListLoader.loadSpellbookAsSpellList(spellbook.spellbookName)
            // Update the mutable state property
            _spellList.value = temporarySpelllist
            if(_selectedSpellbook.value == SpellbookManager.getSpellbook("Favourites")) {
                println("hi sexy")
            }
        }
    }

    fun createSpellbook(spellbookName: String) {
        val newSpellbook = Spellbook(spellbookName)
        spellbooks.add(newSpellbook)
        SpellbookManager.addSpellbook(newSpellbook)
        SpellbookManager.saveSpellbookToFile(newSpellbook.spellbookName)
    }
}

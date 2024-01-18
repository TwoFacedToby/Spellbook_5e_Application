package com.example.spellbook5eapplication.app.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Class
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Model.QuickPlayHandler
import com.example.spellbook5eapplication.app.Repository.SpellDataFetcher
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import kotlinx.coroutines.launch

class QuickPlayViewModel : ViewModel() {

    private val _preventReset = MutableLiveData<Boolean>(false)
    val preventReset: LiveData<Boolean> = _preventReset

    private val _showDialog = MutableLiveData<Boolean>(false)
    val showDialog: LiveData<Boolean> = _showDialog

    fun setPreventReset(preventReset: Boolean) {
        _preventReset.value = preventReset
    }

    fun setShowDialog(showDialog: Boolean){
        _showDialog.value = showDialog
    }

    fun resetValuesIfNeeded() {
        if (_preventReset.value != true) {
            resetValues()
        }
    }

    private var _availableCharacterLevelsLiveDate = MutableLiveData<List<Int>>()
    val availableCharacterLevels: LiveData<List<Int>> = _availableCharacterLevelsLiveDate

    private val _currentClass = mutableStateOf<Class?>(null)
    val currentClass: Class? by _currentClass

    private val _currentCharacterLevel = mutableStateOf<Int?>(null)

    private val _currentQuickPlayNameList = mutableListOf<String>()

    private val _currentQuickPlaySpellList = mutableListOf<Spell.SpellInfo>()

    private val _currentQuickPlaySpellListLiveData = MutableLiveData<List<Spell.SpellInfo>>()
    val currentQuickPlaySpellList: LiveData<List<Spell.SpellInfo>> = _currentQuickPlaySpellListLiveData



    private val _errorInFetchingData = mutableStateOf(false)
    val errorInFetchingData: Boolean by _errorInFetchingData

    private val _errorMessage = mutableStateOf("")
    val errorMessage: String by _errorMessage


    fun updateCurrentClass(newValue: Class) {
        _currentClass.value = newValue
        viewModelScope.launch {
            Log.d("QuickPlayViewModel", "Class: $newValue")
            _availableCharacterLevelsLiveDate.postValue(emptyList())
            val levels = QuickPlayHandler.getAvailableLevelsForClass(newValue)
            Log.d("QuickPlayViewModel", "Levels: $levels")
            _availableCharacterLevelsLiveDate.postValue(levels)
        }
    }
    fun updateCurrentCharacterLevel(newValue: Int){
        _currentCharacterLevel.value = newValue
    }

    fun fetchQuickPlaySpellList() {
        viewModelScope.launch {
            val spellList = QuickPlayHandler.getQuickPlayList(_currentClass.value!!, _currentCharacterLevel.value!!)
            val updatedList = mutableListOf<Spell.SpellInfo>()

            spellList.forEach { spell ->
                SpellDataFetcher.localOrAPI(spell)?.let { fetchedSpell ->
                    updatedList.add(fetchedSpell)
                }
            }

            if (updatedList.size == spellList.size) {
                _currentQuickPlaySpellList.clear()
                _currentQuickPlaySpellList.addAll(updatedList)
                _currentQuickPlayNameList.addAll(spellList)
                _currentQuickPlaySpellListLiveData.value = _currentQuickPlaySpellList.toList() // Notify observers
            } else {
                _errorInFetchingData.value = true
                _errorMessage.value = "We've encountered an error"
            }
        }
    }

    fun clearSpellList() {
        _currentQuickPlayNameList.clear()
        _currentQuickPlaySpellList.clear()
        _currentQuickPlaySpellListLiveData.value = _currentQuickPlaySpellList.toList()
    }

    fun addToSpellBooks(name: String) {
        viewModelScope.launch {
            val spellbook = Spellbook(name)
            _currentQuickPlayNameList.forEach {
                spellbook.addSpellToSpellbook(it)
            }
            SpellbookManager.addSpellbook(spellbook)
            SpellbookManager.saveSpellbookToFile(spellbook.spellbookName)
        }
    }

    fun resetValues() {
        viewModelScope.launch {
            _currentClass.value = null
            _availableCharacterLevelsLiveDate.postValue(emptyList())
            _currentCharacterLevel.value = 0
            _currentQuickPlayNameList.clear()
            _currentQuickPlaySpellList.clear()
            _currentQuickPlaySpellListLiveData.value = _currentQuickPlaySpellList.toList()
        }
    }
}
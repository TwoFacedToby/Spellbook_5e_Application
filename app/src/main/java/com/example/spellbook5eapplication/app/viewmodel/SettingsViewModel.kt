package com.example.spellbook5eapplication.app.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Settings
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Utility.CurrentSettings
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _currentSettings = MutableLiveData<Settings>()
    val currentSettings: LiveData<Settings> = _currentSettings

    init {
        load()
    }
    fun updateSettings(newSettings: Settings) {
        viewModelScope.launch {
            Log.d("Settings", "Updated Settings")
            _currentSettings.postValue(newSettings)
            save() // Save the new settings to persistent storage if needed
        }
    }
    fun resetSettings(){
        viewModelScope.launch {
            Log.d("Settings", "Reset Settings")
            _currentSettings.value!!.resetToDefault()
            Log.d("Settings","New Settings: ${_currentSettings.value.toString()}")
            save()
        }
    }
    fun deleteLocalCache(){
        val current = LocalDataLoader.getIndexList(LocalDataLoader.DataType.INDIVIDUAL)
        current.forEach {
            LocalDataLoader.deleteFile(it, LocalDataLoader.DataType.INDIVIDUAL)
        }
    }
    fun save (){
        val gson = Gson()
        val json = gson.toJson(currentSettings)
        Log.d("Settings Save", "Saving: ${currentSettings.value.toString()}")
        LocalDataLoader.saveJson(json, "settings", LocalDataLoader.DataType.LOCAL)
        CurrentSettings.currentSettings = currentSettings.value!!
    }
    private fun load() {
        val gson = Gson()
        val json = LocalDataLoader.getJson("settings", LocalDataLoader.DataType.LOCAL)
        if(json == null){ //First time, it does not exist yet.
            save() //Save a default/null version of settings
            return
        }
        val newSetting = gson.fromJson(json, Settings::class.java)
        Log.d("LoadedSettings", newSetting.toString())
        _currentSettings.value = newSetting
        CurrentSettings.currentSettings = currentSettings.value!!

    }
}
package com.example.spellbook5eapplication.app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Settings
import com.example.spellbook5eapplication.app.Utility.CurrentSettings
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    private val _currentSettings = MutableLiveData<Settings>()
    val currentSettings: LiveData<Settings> = _currentSettings

    init {
        load()
    }
    fun updateSettings(newSettings: Settings) {
        viewModelScope.launch {
            _currentSettings.postValue(newSettings)
            save()
        }
    }
    fun resetSettings(){
        viewModelScope.launch {
            _currentSettings.value!!.resetToDefault()
            save()
        }
    }
    fun deleteLocalCache(){
        val current = LocalDataLoader.getIndexList(LocalDataLoader.DataType.INDIVIDUAL)
        current.forEach {
            LocalDataLoader.deleteFile(it, LocalDataLoader.DataType.INDIVIDUAL)
        }
    }
    fun save() {
        val gson = Gson()
        val currentSettingsValue = currentSettings.value
        if (currentSettingsValue != null) {
            val json = gson.toJson(currentSettingsValue)
            LocalDataLoader.saveJson(json, "settings", LocalDataLoader.DataType.LOCAL)
            CurrentSettings.currentSettings = currentSettingsValue
        } else {
            val defaultSettings = Settings()
            _currentSettings.value = defaultSettings
            CurrentSettings.currentSettings = defaultSettings
            save()
        }
    }

    private fun load() {
        val gson = Gson()
        val json = LocalDataLoader.getJson("settings", LocalDataLoader.DataType.LOCAL)
        if (json != null) {
            val newSetting = gson.fromJson(json, Settings::class.java)
            _currentSettings.value = newSetting
            CurrentSettings.currentSettings = newSetting
        } else {
            save()
        }
    }
}
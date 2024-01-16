package com.example.spellbook5eapplication.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.UserData
import com.example.spellbook5eapplication.app.Repository.HomeBrewRepository

class SpellCardViewModel(private val repository: HomeBrewRepository) : ViewModel() {

    fun saveHomeBrewSpell(userData: UserData?, data: String, homebrewName: String) {
        userData?.let {
            repository.saveHomeBrewSpell(it.userId, homebrewName, data)
        }
    }

}
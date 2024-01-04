package com.example.spellbook5eapplication.app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.RetroFitAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpellsViewModel : ViewModel() {
    private val _spells = MutableLiveData<List<Spell.SpellNames>>()
    val spells: LiveData<List<Spell.SpellNames>> = _spells
    private val BASE_URL = "https://www.dnd5eapi.co/api/"
    private val TAG: String = "API_RESPONSE"

    fun getSpells() {
        viewModelScope.launch(Dispatchers.IO) {
            val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroFitAPI::class.java)

            try {
                val response = api.getSpells()
                if (response.isSuccessful) {
                    response.body()?.let { spellsResponse ->
                        _spells.postValue(spellsResponse.spells)
                    }
                } else {
                    Log.e(TAG, "Failed to retrieve spells")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }
}

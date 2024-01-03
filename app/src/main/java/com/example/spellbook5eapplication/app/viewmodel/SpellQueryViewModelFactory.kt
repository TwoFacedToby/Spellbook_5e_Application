package com.example.spellbook5eapplication.app.viewmodel

import SpellQueryViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info


object SpellQueryViewModelFactory {
    @Composable
    fun create(type: String): LiveData<List<Spell_Info.SpellInfo?>> {
        val spellQueryViewModel: SpellQueryViewModel = viewModel()
        return spellQueryViewModel.getLiveData(type)
    }
}
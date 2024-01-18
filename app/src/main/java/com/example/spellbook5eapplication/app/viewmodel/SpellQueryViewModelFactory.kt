package com.example.spellbook5eapplication.app.viewmodel

import SpellQueryViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Utility.Displayable


object SpellQueryViewModelFactory {
    @Composable
    fun create(type: String): LiveData<List<Displayable?>> {
        val spellQueryViewModel: SpellQueryViewModel = viewModel()
        return spellQueryViewModel.getLiveData(type)
    }
}
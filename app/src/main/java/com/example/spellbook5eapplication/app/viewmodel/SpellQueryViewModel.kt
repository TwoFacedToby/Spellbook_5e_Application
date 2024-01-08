package com.example.spellbook5eapplication.app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import kotlinx.coroutines.launch

class SpellQueryViewModel() : ViewModel() {

    //Spell name list for API
    private var spellList: SpellList? = null

    //Loading from pagination
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    //All spells with API and Pagination
    private val _spells = MutableLiveData<List<Displayable?>>()
    val spells: LiveData<List<Displayable?>> = _spells

    //Favorite spells from local
    private val _favorite = MutableLiveData<List<Displayable?>>()
    val favorite: LiveData<List<Displayable?>> = _favorite

    //Homebrew spells from local
    private val _homebrew = MutableLiveData<List<Displayable?>>()
    val homebrew: LiveData<List<Displayable?>> = _homebrew

    //All spellbooks
    private val _spellBooks = MutableLiveData<List<Displayable?>>()
    val spellBooks: LiveData<List<Displayable?>> = _spellBooks

    //Is all spells loaded
    private val _allSpellsLoaded = MutableLiveData<Boolean>()
    val allSpellsLoaded: LiveData<Boolean> = _allSpellsLoaded
    private var _allSpells = SpellList()





    fun getLiveData(type: String): LiveData<List<Displayable?>> {
        loadFavoriteSpells()
        loadHomebrewList()
        loadSpellBooks()
        return when (type) {
            "ALL_SPELLS" -> spells
            "FAVORITES" -> favorite
            "HOMEBREW" -> homebrew
            "SPELLBOOK" -> spellBooks
            else -> throw IllegalArgumentException("Invalid type")
        }
    }

    init {
        loadSpellList()
    }

    private fun loadSpellList() {
        viewModelScope.launch {
            println("Kig her")
            spellList = SpellController.getAllSpellsList()
            Log.d("com.example.spellbook5eapplication.app.viewmodel.SpellQueryViewModel", "init: ${spellList?.getIndexList()?.size}")
            loadInitialSpells()
        }
    }

    private fun loadInitialSpells() {
        viewModelScope.launch {
            // Load initial spells only if the spell list is empty
            if (_spells.value.isNullOrEmpty()) {
                _isLoading.postValue(true)
                val initialSpells = SpellController.loadNextFromSpellList(10, spellList!!)
                val displayableSpells = initialSpells?.map { it as Displayable }
                _spells.postValue(displayableSpells)
                _isLoading.postValue(false)
            }
        }
    }

    fun loadMoreSpells() {
        viewModelScope.launch {
            if (canLoadMoreSpells()) {
                _isLoading.postValue(true)
                val nextSpells = SpellController.loadNextFromSpellList(10, spellList!!)
                // Convert each SpellInfo to Displayable
                val displayableNextSpells = nextSpells?.map { it as Displayable }
                val updatedList = _spells.value.orEmpty().toMutableList()
                updatedList.addAll(displayableNextSpells!!)
                _spells.postValue(updatedList)
                _isLoading.postValue(false)
            }
        }
    }

    private fun loadFavoriteSpells() {
        val spellList = SpelllistLoader.loadFavouritesAsSpellList()
        val displayableFavorites = spellList.getSpellInfoList().map { it as Displayable }
        _favorite.postValue(displayableFavorites)
    }

    private fun loadHomebrewList(){
        val spellList = SpellController.retrieveHomeBrew()
        val displayableHomebrews = spellList!!.getSpellInfoList().map { it as Displayable }
        _homebrew.postValue(displayableHomebrews)
    }

    private fun loadSpellBooks(){
        val spellBookList = SpellbookManager.getAllSpellbooks()
        _spellBooks.postValue(spellBookList)
    }

    fun canLoadMoreSpells(): Boolean {
        return (_spells.value?.size ?: 0) < spellList!!.getIndexList().size
    }

    fun totalSpellsLoaded(): Int = spellList!!.getIndexList().size

    fun loadSpellsBasedOnFilter(filter: Filter) {
        Log.d("com.example.spellbook5eapplication.app.viewmodel.SpellQueryViewModel", "LoadSpellsBasedOnFilter start: ${filter.toString()}")

        viewModelScope.launch {

            SpellController.loadEntireSpellList(spellList!!)

            _allSpells = spellList as SpellList

            _allSpellsLoaded.postValue(true)

            val filteredSpells = SpellController.searchSpellListWithFilter(spellList!!, filter)

            _spells.postValue(filteredSpells.getSpellInfoList())

            /*_isLoading.postValue(true)

            SpellController.loadEntireSpellList(spellList!!)
            val filteredSpells = SpellController.searchSpellListWithFilter(spellList!!, filter)
            Log.d("com.example.spellbook5eapplication.app.viewmodel.SpellQueryViewModel", "FilteredSpellList: $spellList")

            val displayableSpells = filteredSpells.getSpellInfoList().map { it }
            _spells.postValue(displayableSpells)

            _isLoading.postValue(false)*/
        }
    }

    fun loadSecondTimeFilter(filter: Filter){

        val filteredSpells = SpellController.searchSpellListWithFilter(_allSpells, filter)
        _spells.postValue(filteredSpells.getSpellInfoList())

    }

}
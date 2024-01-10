import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.Utility.Search
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.viewmodel.SpellsViewModel
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

    private var _indexListFromAPI = mutableListOf<String>()
    var indexListFromAPI: List<String> = _indexListFromAPI


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
            spellList = SpellsViewModel.fetchAllSpellNames()
            _indexListFromAPI = spellList!!.getIndexList().toMutableList()
            Log.d("API_RESPONSE_NEW", spellList.toString())
            loadInitialSpells()
        }
    }

    private fun loadInitialSpells() {
        viewModelScope.launch {

            Log.d("API_RESPONSE_NEW",_spells.value.isNullOrEmpty().toString())

            // Load initial spells only if the spell list is empty
            if (_spells.value.isNullOrEmpty()) {
                _isLoading.postValue(true)
                val initialSpells = SpellsViewModel.fetchNextSpellDetails(spellList!!, 10)
                Log.d("API_RESPONSE_NEW","IS: " + initialSpells.toString())
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
                val nextSpells = SpellsViewModel.fetchNextSpellDetails(spellList!!, 10)
                val displayableNextSpells = nextSpells.map { it as Displayable }

                val updatedList = _spells.value.orEmpty().toMutableList()
                updatedList.addAll(displayableNextSpells)
                _spells.postValue(updatedList)
                _isLoading.postValue(false)
            }
        }
    }



    private fun loadFavoriteSpells() {
        viewModelScope.launch {
            val spellList = SpelllistLoader.loadSpellbookAsSpellList("Favourites")
            Log.d("MILK2", spellList.toString())

            val displayableFavorites = spellList.getSpellInfoList().map { it as Displayable }

            _favorite.postValue(displayableFavorites)
        }
    }

    private fun loadHomebrewList(){
        spellList?.setIndexList(LocalDataLoader.getIndexList(LocalDataLoader.DataType.HOMEBREW))
        val displayableHomebrews = spellList?.getSpellInfoList()?.map { it as Displayable }
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
        Log.d("SpellQueryViewModel", "LoadSpellsBasedOnFilter start: ${filter.toString()}")
        viewModelScope.launch {
            _isLoading.postValue(true)

            spellList!!.setSpellInfoList(SpellController.loadSpells(spellList!!.getIndexList()))
            val search = Search()

            val filteredSpells = search.searchSpellListWithFilter(spellList!!, filter)
            Log.d("SpellQueryViewModel", "FilteredSpellList: $spellList")

            val displayableSpells = filteredSpells.getSpellInfoList().map { it }
            _spells.postValue(displayableSpells)

            _isLoading.postValue(false)
        }
    }
}
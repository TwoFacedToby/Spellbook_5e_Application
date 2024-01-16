import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.QuickPlay
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.Utility.Search
import com.example.spellbook5eapplication.app.Repository.SpellDataFetcher
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Repository.SpelllistLoader
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.view.spellCards.bottomDistance
import com.example.spellbook5eapplication.app.viewmodel.SpellsViewModel
import com.example.spellbook5eapplication.app.viewmodel.TitleState
import kotlinx.coroutines.delay
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
        if(TitleState.currentTitle.value == null){
            loadSpellBooks()
        }

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
            if(spellList!!.getIndexList().isEmpty()){
                val list = LocalDataLoader.getIndexList(LocalDataLoader.DataType.INDIVIDUAL)
                spellList!!.setIndexList(list)

            }
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
                val loadedSpells = SpellsViewModel.fetchNextSpellDetails(spellList!!, 10)
                val loadingSpells = mutableListOf<Spell.SpellInfo>()
                for(i in 0 until spellList!!.getIndexList().count()-10){
                    loadingSpells.add(SpellsViewModel.getEmptySpell())
                }
                val initialSpells = loadedSpells + loadingSpells
                Log.d("API_RESPONSE_NEW","IS: " + initialSpells.toString())
                val displayableSpells = initialSpells?.map { it as Displayable }
                _spells.postValue(displayableSpells)
                _isLoading.postValue(false)
            }
        }
    }

    fun loadMoreSpells() {
        viewModelScope.launch {
            if (canLoadMoreSpells() && spellList!!.getLoaded() < spellList!!.getIndexList().count()) {
                _isLoading.postValue(true)
                val nextSpells = SpellsViewModel.fetchNextSpellDetails(spellList!!, 10)
                val displayableNextSpells = nextSpells.map { it as Displayable }

                val updatedList = _spells.value.orEmpty().toMutableList()

                var loaded = 0

                for (i in updatedList.indices) {
                    if (loaded == nextSpells.size) break
                    when (val displayable = updatedList[i]) {
                        is Spell.SpellInfo -> {
                            if (displayable.index == null) {
                                updatedList[i] = nextSpells[loaded++]
                            }
                        }
                    }
                }
                _spells.postValue(updatedList)
                _isLoading.postValue(false)
            }
        }
    }

    fun shouldLoadMoreData(listState: LazyListState): Boolean {
        val layoutInfo = listState.layoutInfo
        val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        val loaded = try { spellList!!.getLoaded() }
        catch (e : Exception){ 0 } //Loaded is null
        val toReturn = lastVisibleItem > (loaded - 3) //We minus by three, because we want it to start loading before we reach the bottom
        Log.d("SpellQuery", "Should load more: $toReturn\nLast: $lastVisibleItem\nLoaded: $loaded")
        return toReturn
    }



    private fun loadFavoriteSpells() {
        viewModelScope.launch {
            val spellList = SpelllistLoader.loadSpellbookAsSpellList("favourites")
            Log.d("MILK2", spellList.toString())

            val displayableFavorites = spellList.getSpellInfoList().map { it as Displayable }

            _favorite.postValue(displayableFavorites)
        }
    }

    fun loadHomebrewList(){
        viewModelScope.launch {
            val spellList = SpelllistLoader.loadHomeBrewSpellList()
            val displayableHomebrews = spellList.getSpellInfoList().map { it as Displayable }
            _homebrew.postValue(displayableHomebrews)
        }

    }
    fun loadQuickPlayer(){
        viewModelScope.launch {
            val spellBookList = SpellbookManager.getAllSpellbooks()
            _spellBooks.postValue(spellBookList)



            val spellList = SpellList()
            val indexList = QuickPlay.getQuickPlayList(QuickPlay.Class.WIZARD, 6)
            spellList.setIndexList(indexList)




        }
    }

    fun loadSpellBooks(){
        val spellBookList = SpellbookManager.getAllSpellbooks()
        _spellBooks.postValue(spellBookList)
    }

    fun canLoadMoreSpells(): Boolean {
        if(!_spells.isInitialized) return false
        _spells.value!!.forEach {
            val spell : Spell.SpellInfo = it as Spell.SpellInfo
            if(spell.index == null) return true
        }
        return false
    }

    fun totalSpellsLoaded(): Int = spellList!!.getIndexList().size

    fun loadSpellsFromSpellbook(spellbook: Spellbook) {


        Log.d("MAY", spellbook.toString())

        viewModelScope.launch {
            val spellInfos = spellbook.spells.mapNotNull { spellName ->
                try {
                    SpellDataFetcher.localOrAPI(spellName)
                } catch (e: Exception) {
                    Log.e("SpellQueryViewModel", "Error fetching spell info for $spellName", e)
                    null
                }
            }

            Log.d("MAY", spellInfos.toString())

            _spellBooks.postValue(spellInfos)
        }
    }

    fun loadSpellsBasedOnFilter(filter: Filter) {
        Log.d("SpellQueryViewModel", "LoadSpellsBasedOnFilter start: ${filter.toString()}")

        _isLoading.value = true
        _spells.value = emptyList()

        //SpellList.Hide()
        viewModelScope.launch {


            delay(1)
            /*
            Necessary delay, please don't remove me.
            without this delay, the list is not emptied, as it knows it is gonna be filled shortly thereafter.
            It is just 1 millisecond, it is okay. We'll live love laugh.
            * */

            //If it is not loaded fully -> load it fully.
            if(spellList!!.getLoaded() < spellList!!.getIndexList().count()){
                val spellInfoList = mutableListOf<Spell.SpellInfo>()
                for(spell in spellList!!.getIndexList())
                {
                    spellInfoList.add(SpellDataFetcher.localOrAPI(spell)!!)
                }
                spellList!!.setSpellInfoList(spellInfoList)
                spellList!!.setLoaded(spellList!!.getIndexList().count())
            }

            val search = Search()
            val filteredSpells = search.searchSpellListWithFilter(spellList!!.copy(), filter)

            val displayableSpells = filteredSpells.getSpellInfoList().map { it }
            _spells.postValue(displayableSpells)
            //SpellList.Show()
            _isLoading.postValue(false)

        }

    }
}
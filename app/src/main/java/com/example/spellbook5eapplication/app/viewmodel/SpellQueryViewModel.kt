import android.os.Debug
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController
import kotlinx.coroutines.launch

class SpellQueryViewModel : ViewModel() {
    private val _spells = MutableLiveData<List<Spell_Info.SpellInfo?>>()
    val spells: LiveData<List<Spell_Info.SpellInfo?>> = _spells
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var spellList: SpellList? = null

    init {
        loadSpellList()
    }

    private fun loadSpellList() {
        viewModelScope.launch {
            println("Kig her")
            spellList = SpellController.getAllSpellsList()
            loadInitialSpells()
        }
    }

    private fun loadInitialSpells() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val initialSpells = SpellController.loadNextFromSpellList(10, spellList!!)
            _spells.postValue(initialSpells)
            _isLoading.postValue(false)
        }
    }

    fun loadMoreSpells() {
        viewModelScope.launch {
            if (canLoadMoreSpells()) {
                _isLoading.postValue(true)
                val nextSpells = SpellController.loadNextFromSpellList(10, spellList!!)
                val updatedList = _spells.value.orEmpty().toMutableList()
                if (nextSpells != null) {
                    updatedList.addAll(nextSpells)
                }
                _spells.postValue(updatedList)
                _isLoading.postValue(false)
            }
        }
    }

    fun canLoadMoreSpells(): Boolean {
        return _spells.value?.size ?: 0 < spellList!!.getIndexList().size
    }

    fun totalSpellsLoaded(): Int = spellList!!.getIndexList().size
}

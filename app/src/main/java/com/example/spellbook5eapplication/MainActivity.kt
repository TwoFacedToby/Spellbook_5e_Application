package com.example.spellbook5eapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Model.Favourites
import com.example.spellbook5eapplication.app.Model.RetroFitAPI
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.viewmodel.SpellsViewModel
import com.example.spellbook5eapplication.app.viewmodel.SpellsViewModelFactory
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    // Define a CoroutineScope for launching coroutines
    //private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: SpellsViewModel
    private val TAG: String = "API_RESPONSE_NEW"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpelllistLoader.loadSpellbooks()

        // Set the context for the SpellController
        SpellController.setContext(applicationContext)

        val factory = SpellsViewModelFactory(applicationContext)
        viewModel = ViewModelProvider(this, factory)[SpellsViewModel::class.java]

        // Observe the spellsOverview LiveData for changes
        viewModel.spellsOverview.observe(this, Observer { spellNames ->
            // This list will contain the indices of all spells
            val indices = spellNames.mapNotNull { it.index }
            spellNames.forEach {
                Log.e(TAG, "Spellbook test: $it")
            }

            // Fetch the details for all spells
            viewModel.getSpellsDetails(indices)
        })

        viewModel.spellDetails.observe(this, Observer { spellDetails ->
            spellDetails.forEach { spellDetail ->
                Log.e(TAG, "Spell detail: ${spellDetail.name}")
            }
        })

        viewModel.fetchAllSpellNames()


        setContent {
            Spellbook5eApplicationTheme {
                MainScreen(SpellController, SpelllistLoader)
            }
        }
    }
}

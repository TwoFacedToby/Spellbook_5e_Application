package com.example.spellbook5eapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpelllistLoader.loadSpellbooks()

        viewModel = ViewModelProvider(this).get(SpellsViewModel::class.java)
        viewModel.getSpells()


        // Initialize SpellController with context
        SpellController.setContext(applicationContext)
        setContent {
            Spellbook5eApplicationTheme {
                MainScreen(SpellController, SpelllistLoader)

            }
        }

    }


}

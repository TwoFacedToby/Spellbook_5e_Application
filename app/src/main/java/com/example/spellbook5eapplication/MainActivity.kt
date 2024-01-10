package com.example.spellbook5eapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Model.DND5eAPI
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Favourites
import com.example.spellbook5eapplication.app.Model.RetroFitAPI
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.example.spellbook5eapplication.app.Model.SpellFactory
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellDataFetcher
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.viewmodel.SpellsViewModel
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalDataLoader.setContext(applicationContext)

        SpelllistLoader.loadSpellbooks()
        lifecycleScope.launch {
            SpellDataFetcher.sneakyQuickLoader()
        }


        setContent {
            Spellbook5eApplicationTheme {
                MainScreen(SpellController, SpelllistLoader)
            }
        }
    }
}

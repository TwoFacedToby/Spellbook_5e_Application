package com.example.spellbook5eapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
    private val BASE_URL = "https://www.dnd5eapi.co/api/"
    private val TAG: String = "API_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpelllistLoader.loadSpellbooks()


        // Initialize SpellController with context
        SpellController.setContext(applicationContext)
        setContent {
            Spellbook5eApplicationTheme {
                getSpells()
                MainScreen(SpellController, SpelllistLoader)

            }
        }
        /*scope.launch {
            runBlocking {
                networkRequest() { spellList ->
                    spellList.printInfoToConsole()
                }
            }
        }*/
    }

    private fun getSpells() {
        CoroutineScope(Dispatchers.IO).launch {
            val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroFitAPI::class.java)

            try {
                val response = api.getSpells()
                if (response.isSuccessful) {
                    response.body()?.let { spellsResponse ->
                        withContext(Dispatchers.Main) {
                            for (spell in spellsResponse.spells) {
                                Log.i(TAG, "Name: ${spell.name}")
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "Failed to retrieve spells")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }


    /*
private fun networkRequest(callback: (result: SpellList) -> Unit) {
    scope.launch {
        val spellList = SpellController.getAllSpellsList()
        if (spellList != null) {
            SpellController.loadEntireSpellList(spellList)
            // Do something with the spell list, like updating the UI
            callback(spellList)
        }
    }
}
fun exampleFilter() : Filter {
    val filter = Filter()
    filter.setSpellName("Fire")
    return filter
}

*/
    }

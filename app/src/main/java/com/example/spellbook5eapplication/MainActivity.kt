package com.example.spellbook5eapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Favourites
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    // Define a CoroutineScope for launching coroutines
    //private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SpelllistLoader.loadSpellbooks()


        // Initialize SpellController with context
        SpellController.setContext(applicationContext)
        setContent {
            Spellbook5eApplicationTheme {
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

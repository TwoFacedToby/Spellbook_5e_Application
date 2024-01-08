package com.example.spellbook5eapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spellbook5eapplication.app.Model.DND5eAPI
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.SpellFactory
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    // Define a CoroutineScope for launching coroutines
    //private val scope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SpelllistLoader.loadSpellbooks()


        // Initialize SpellController with context
        LocalDataLoader.setContext(applicationContext)
        val list = LocalDataLoader.getIndexList(LocalDataLoader.DataType.INDIVIDUAL)
        println("Printing local list")
        for (s in list) {
            println(s)
        }
        runBlocking {  SpellController.getAllSpellsList()  }

        setContent {
            Spellbook5eApplicationTheme {
                MainScreen(SpellController, SpelllistLoader)
            }
        }
    }

}

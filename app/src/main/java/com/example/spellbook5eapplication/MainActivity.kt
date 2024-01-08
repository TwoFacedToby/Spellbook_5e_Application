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

        val factory = SpellFactory()
        val api = DND5eAPI()

        val json = api.getDnD5eSpell("acid-splash");
        var spell : Spell
        if(json != null)
        {
            spell = factory.createSpellFromJson(json)
            println("name: ${spell.name}")
            println("casting time: ${spell.castingTime}")
            println("classes: ${spell.classes.toString()}")
        }
        else println("json was null")






        SpelllistLoader.loadSpellbooks()


        // Initialize SpellController with context
        LocalDataLoader.setContext(applicationContext)
        LocalDataLoader.getIndexList(LocalDataLoader.DataType.INDIVIDUAL)
        runBlocking {  SpellController.getAllSpellsList()  }

        setContent {
            Spellbook5eApplicationTheme {
                MainScreen(SpellController, SpelllistLoader)
            }
        }
    }

}

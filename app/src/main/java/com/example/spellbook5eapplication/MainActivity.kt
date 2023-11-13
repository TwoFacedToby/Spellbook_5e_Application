package com.example.spellbook5eapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spellbook5eapplication.app.view.MainScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    // Define a CoroutineScope for launching coroutines
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var spellbookManager: SpellbookManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize SpellController with context
        SpellController.setContext(applicationContext)

        spellbookManager = SpellbookManager()
        setContent {
            Spellbook5eApplicationTheme {
                MainScreen()
            }
        }
        scope.launch {
            runBlocking {
                networkRequest() { spellList ->
                    spellList.printInfoToConsole()
                }
            }
        }
    }

    private fun addMockSpellbooks() {
        spellbookManager.addSpellbook(Spellbook("Wizard's Handbook"))
        spellbookManager.addSpellbook(Spellbook("Cleric's Compendium"))
    }

    private fun addMockSpellsToSpellbooks(){
        spellbookManager.getSpellbook("Wizard's Handbook")?.addSpell("Fire Ball - of course")
    }

    private fun saveSpellbooks() {
        // Get all spellbooks from your manager
        spellbookManager.getAllSpellbooks().forEach { spellbook ->
            // Use the spellbook's name for the file name
            val spellBookFileName = spellbook.spellbookName

            // Save each spellbook to a file named after the spellbook itself
            spellbookManager.saveSpellbookToFile(spellBookFileName)
        }
    }

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
}

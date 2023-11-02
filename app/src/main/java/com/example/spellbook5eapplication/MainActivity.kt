package com.example.spellbook5eapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.launch {
            runBlocking {
                networkRequest { spellList ->
                    spellList.printInfoToConsole()
                }
            }
        }
        setContent {
            Spellbook5eApplicationTheme {
                MainScreen()
            }
        }
    }
}
fun networkRequest(callback: (result:SpellList) -> Unit){
    val controller = SpellController()
    val spellList = controller.getAllSpellsList()
    if(spellList != null){
        controller.loadSpellList(spellList)
        val filter = exampleFilter()
        controller.searchSpellListWithFilter(spellList, filter)
        callback(spellList)
    }
}
fun exampleFilter() : Filter{
    val filter = Filter()
    filter.setSpellName("Fire")
    return filter
}
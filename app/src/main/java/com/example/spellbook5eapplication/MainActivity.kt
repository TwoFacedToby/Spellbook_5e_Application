package com.example.spellbook5eapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

        setContent {
            Spellbook5eApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        scope.launch {
            runBlocking {
                networkRequest { spellList ->
                    spellList.printInfoToConsole()
                }
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Spellbook5eApplicationTheme {
        Greeting("Android")
    }
}
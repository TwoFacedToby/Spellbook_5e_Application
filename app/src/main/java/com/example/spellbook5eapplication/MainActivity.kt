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
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme

class MainActivity : ComponentActivity() {
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

        val controller = SpellController()
        val spellList = controller.getAllSpellsList()
        if(spellList != null){
            controller.loadSpellList(spellList)
            val filter = Filter()
            filter.addDamageType(Filter.Damage_Type.FIRE)
            filter.addSchool(Filter.School.EVOCATION)
            filter.addLevel(2)
            filter.addLevel(3)
            controller.searchSpellListWithFilter(spellList, filter)
            spellList.printInfoToConsole()
        }



    }
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
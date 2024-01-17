package com.example.spellbook5eapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.Repository.SpellController
import com.example.spellbook5eapplication.app.Repository.SpellDataFetcher
import com.example.spellbook5eapplication.app.Repository.SpelllistLoader
import com.example.spellbook5eapplication.ui.theme.SpellbookTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalDataLoader.setContext(applicationContext)
        SpelllistLoader.loadSpellbooks()
        lifecycleScope.launch {
            SpellDataFetcher.sneakyQuickLoader()
        }


        setContent {
            val isDarkTheme = true
            SpellbookTheme(isDarkTheme) {
                MainScreen()
            }
        }
    }
}

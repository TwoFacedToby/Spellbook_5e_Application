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
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var auth: FirebaseAuth
        LocalDataLoader.setContext(applicationContext)

        SpelllistLoader.loadSpellbooks()
        lifecycleScope.launch {
            SpellDataFetcher.sneakyQuickLoader()
        }


        // Initialize Firebase Auth
        auth = Firebase.auth
        setContent {
            Spellbook5eApplicationTheme {
                MainScreen()
            }
        }
    }
}

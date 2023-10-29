package com.example.spellbook5eapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.ui.theme.Spellbook5eApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Spellbook5eApplicationTheme {
                MainScreen()
            }
        }
    }
}
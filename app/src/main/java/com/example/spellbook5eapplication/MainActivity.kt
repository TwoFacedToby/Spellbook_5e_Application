package com.example.spellbook5eapplication

import GoogleAuthUIClient
import SignInViewModel
import android.app.Activity
import android.content.IntentSender
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.app.view.MainScreen
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.Repository.SpellDataFetcher
import com.example.spellbook5eapplication.app.Repository.SpelllistLoader
import com.google.android.gms.auth.api.identity.Identity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spellbook5eapplication.app.view.AuthUI.SignInIntentSender
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.example.spellbook5eapplication.ui.theme.SpellbookTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUIClient by lazy { GoogleAuthUIClient(this) }
    private val signInViewModel by lazy { SignInViewModel(googleAuthUIClient) }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalDataLoader.setContext(applicationContext)
        SpelllistLoader.loadSpellbooks()


        lifecycleScope.launch {
            SpellDataFetcher.sneakyQuickLoader()
        }

        setContent {
            SpellbookTheme {
                MainScreen(signInViewModel)
            }
        }
    }


}

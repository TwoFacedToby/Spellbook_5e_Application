package com.example.spellbook5eapplication.app.view.AuthUI

import SignInWithGoogle
import SignInViewModel
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CreateAccountScreen(
    signInViewModel: SignInViewModel,
    navController: NavController
) {
    val signInResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                signInViewModel.onSignInResult(intent)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Email and Password fields here

        Button(onClick = { /* Handle email/password login */ }) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        SignInWithGoogle(signInViewModel, signInResultLauncher)

        Spacer(modifier
        = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("CreateAccount") }) {
            Text("Create Account")
        }
    }
}
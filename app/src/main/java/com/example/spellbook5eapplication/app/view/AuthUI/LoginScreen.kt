package com.example.spellbook5eapplication.app.view.AuthUI

import SignInWithGoogle
import SignInViewModel
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import com.example.spellbook5eapplication.R

@Composable
fun LoginScreen(
    signInViewModel: SignInViewModel,
    navController: NavController
) {
    val signInResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // ... existing code ...
    }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .padding(top = 50.dp)
            .padding(bottom = 50.dp)
            .border(1.dp, Color.Gray)
            .padding(16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(onClick = { /*...*/ }) {
                Text("Log In")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("create_account_screen") }) {
                Text("Create Account")
            }
        }
    }
}

    @Composable
    fun SignInWithGoogle(signInViewModel: SignInViewModel, signInResultLauncher: ActivityResultLauncher<Intent>) {
        val context = LocalContext.current
        val drawable = ContextCompat.getDrawable(context, R.drawable.android_light_rd_si_4x)
        val imageBitmap = drawable?.toBitmap()?.asImageBitmap()

        imageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = "Sign in with Google",
                modifier = Modifier
                    .size(192.dp, 48.dp)
                    .clickable {
                        signInViewModel.signInWithGoogle { intentSenderRequest ->
                            signInResultLauncher.launch(intentSenderRequest)
                        }
                    }
            )
        }
    }
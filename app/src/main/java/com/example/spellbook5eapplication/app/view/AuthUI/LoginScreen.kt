package com.example.spellbook5eapplication.app.view.AuthUI

import SignInWithGoogle
import SignInViewModel
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
    navController: NavController // Assuming you are using NavController for navigation
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
                    .size(192.dp, 48.dp) // Adjust the size to fit your layout
                    .clickable {
                        signInViewModel.signInWithGoogle { intentSenderRequest ->
                            signInResultLauncher.launch(intentSenderRequest)
                        }
                    }
            )
        }
    }
}
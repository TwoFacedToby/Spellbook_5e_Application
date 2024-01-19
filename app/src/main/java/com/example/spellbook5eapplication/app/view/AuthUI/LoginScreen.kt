package com.example.spellbook5eapplication.app.view.AuthUI

import SignInViewModel
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.spellbook5eapplication.app.Utility.SignInEvent
import com.example.spellbook5eapplication.app.view.viewutilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState

@Composable
fun LoginScreen(
    signInViewModel: SignInViewModel,
    onDismissRequest: () -> Unit,
    navController: NavController
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(signInViewModel) {
        signInViewModel.eventFlow.collect { event ->
            when (event) {
                is SignInEvent.SignInSuccess -> {
                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate("search_screen")
                }

                is SignInEvent.SignInFailure -> {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }

                is SignInEvent.SignOutSuccess -> {
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                }

                is SignInEvent.DismissOverlay -> {
                    onDismissRequest()
                }
                is SignInEvent.CreateAccountFailed -> {
                    Toast.makeText(context, "Create account failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(modifier = Modifier.clickable(onClick = { GlobalOverlayState.dismissOverlay() })) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Button(onClick = { signInViewModel.signInEmail(username, password)}) {
                Text("Sign In")
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { navController.navigate("create_account_screen") }) {
                Text("Create Account")
            }
        }
    }

    @Composable
    fun SignInWithGoogle(
        signInViewModel: SignInViewModel,
        signInResultLauncher: ActivityResultLauncher<Intent>
    ) {
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
}
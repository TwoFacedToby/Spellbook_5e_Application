package com.example.spellbook5eapplication.app.view.Overlays

import SignInViewModel
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import kotlinx.coroutines.launch

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spellbook5eapplication.app.view.AuthUI.GoogleAuthUIClient
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun UserOverlay(
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    val signInClient = Identity.getSignInClient(context)
    val viewModel = remember { SignInViewModel(googleAuthUIClient = GoogleAuthUIClient(context, signInClient)) }
    val signInState by viewModel.state.collectAsStateWithLifecycle()
    // ActivityResultLauncher for Google Sign-In
    val signInResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                viewModel.onSignInResult(intent)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User",
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Conditionally display content based on sign-in state
        if (signInState.isSignInSuccessful) {
            // Display user profile information
            Text("User is signed in")
            // Add a sign-out button
            Button(onClick = { viewModel.onSignOutClick() }) {
                Text("Sign Out")
            }
        } else {
            Button(onClick = {
                viewModel.signInWithGoogle { intentSenderRequest ->
                    signInResultLauncher.launch(intentSenderRequest)
                }
            }) {
                Text("Sign In with Google")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onDismissRequest) {
            Text("Dismiss")
        }
    }
}
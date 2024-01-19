import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.UserData
import com.example.spellbook5eapplication.app.Utility.GlobalLogInState
import com.example.spellbook5eapplication.app.Utility.JsonTokenManager.loadAllSpellsFromFirebase
import com.example.spellbook5eapplication.app.Utility.JsonTokenManager.saveTokenAsHomebrew
import com.example.spellbook5eapplication.app.Utility.SignInEvent
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.google.firebase.auth.FirebaseAuth


@Composable
fun UserOverlay(
    signInViewModel: SignInViewModel,
    onDismissRequest: () -> Unit,
    navHostController: NavController
) {
    val signInState by signInViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }



    LaunchedEffect(signInViewModel) {
        signInViewModel.eventFlow.collect { event ->
            when (event) {
                is SignInEvent.SignInSuccess -> {
                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
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

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (GlobalLogInState.isloggedIn) {
            val spellQueryViewModel: SpellQueryViewModel = viewModel()
            Log.d("signInStateData", "signInStateData: ${signInState.data}")
            Text("Name: ${GlobalLogInState.userId}")
            signInState.data?.let { userData ->
                UserCard(userData)
            }
                Spacer(modifier = Modifier.height(16.dp))

                // Button to import homebrew
                Button(onClick = { showDialog.value = true }) {
                    Text("Import Homebrew")
                }
                ImportHomebrewDialog(showDialog = showDialog) { token ->
                    saveTokenAsHomebrew(token)
                    if(token != null){
                        Toast.makeText(context, "Homebrew imported", Toast.LENGTH_SHORT).show()
                        spellQueryViewModel.loadHomebrewList()
                    }
                }

                Button(onClick = { loadAllSpellsFromFirebase { Toast.makeText(context, "Homebrews restored", Toast.LENGTH_LONG).show()
                    spellQueryViewModel.loadHomebrewList()} }) {
                    Text("Restore all Homebrews")
                    }

                SignOutButton(signInViewModel) {
                    onDismissRequest()
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                            if (GlobalOverlayState.getOverlayStack().isNotEmpty()) {
                                GlobalOverlayState.dismissOverlay()
                            }
                            navHostController.navigate(Screens.Login.route) {
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                    }) {
                        Text(text = "Sign In")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

@Composable
fun UserCard(userData: UserData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.d("UserCard", "UserCard: ${userData.name}, ${userData.userId}, ${userData.profilePictureUrl}")

            Text(text = GlobalLogInState.userId ?: "", fontWeight = FontWeight.Bold)
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

@Composable
fun SignOutButton(signInViewModel: SignInViewModel, onSignOut: () -> Unit) {

    Button(onClick = {
        signInViewModel.onSignOutClick()
        onSignOut()}) {
        Text("Sign Out")



    }
}

@Composable
fun ImportHomebrewDialog(
    showDialog: MutableState<Boolean>,
    onSaveToken: (String) -> Unit
) {
    val tokenInput = remember { mutableStateOf("") }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Import Homebrew Token") },
            text = {

                val scrollState = rememberScrollState()

                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    TextField(
                        value = tokenInput.value,
                        onValueChange = { tokenInput.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 10,
                        textStyle = TextStyle(fontFamily = FontFamily.Monospace)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSaveToken(tokenInput.value)
                        showDialog.value = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

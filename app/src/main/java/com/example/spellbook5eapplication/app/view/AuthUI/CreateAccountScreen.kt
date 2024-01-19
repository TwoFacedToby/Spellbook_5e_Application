import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spellbook5eapplication.app.Utility.SignInEvent

@Composable
fun CreateAccountScreen(
    signInViewModel: SignInViewModel,
    onDismissRequest: () -> Unit,
    navController: NavController
) {
    // State variables to hold user input
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var context = LocalContext.current



    fun handleCreateAccount() {
        nameError = name.isEmpty()
        emailError = email.isEmpty()
        passwordError = password.isEmpty()

        if (!(nameError || emailError || passwordError)) {
                signInViewModel.createAccountWithEmail(email, password, name)
        }
    }

    val signInResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                signInViewModel.onSignInResult(intent)
            }
        }
    }

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

                is SignInEvent.CreateAccountFailedAlternative -> {
                    Toast.makeText(context, "Create account failed", Toast.LENGTH_SHORT).show()
                }

                is SignInEvent.CreateAccountSuccess -> {
                    Toast.makeText(context, "Create account success", Toast.LENGTH_SHORT).show()
                    navController.navigate("search_screen")
                }
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
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = false
            },
            label = { Text("Name") },
            isError = nameError
        )
        if (nameError) {
            Text("Please enter a name", color = Color.Red)
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = { Text("Email") },
            isError = emailError
        )
        if (emailError) {
            Text("Please enter an email", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = passwordError
        )
        if (passwordError) {
            Text("Please enter a password", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Create account button
        Button(
            onClick = { handleCreateAccount()
            }
        ) {
            Text("Create Account")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login_screen") }, modifier = Modifier.background(Color.DarkGray)) {
            Text("Already have an account? Log in")
        }
    }

}


import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.UserData
import com.example.spellbook5eapplication.app.Utility.SignInEvent
import com.example.spellbook5eapplication.app.view.AuthUI.CreateAccountScreen
import com.example.spellbook5eapplication.app.view.AuthUI.LoginScreen
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun UserOverlay(
    signInViewModel: SignInViewModel,
    onDismissRequest: () -> Unit,
) {
    val signInState by signInViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current


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

        if (signInState.isSignInSuccessful) {
            signInState.data?.let { userData ->
                UserCard(userData)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {val database = Firebase.database
                    val myRef = database.getReference("User/${signInState.data?.userId}/${signInState.data?.name}")

                    myRef.setValue("Hello, World!")
                    Log.d("myRef123", myRef.toString())}) {
                    Text("Import Homebrew")
                }
                SignOutButton(signInViewModel){
                    onDismissRequest()
                }
            }

        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SignInWithGoogle(signInViewModel, signInResultLauncher)
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
            Text(text = userData.name ?: "", fontWeight = FontWeight.Bold)
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
                .size(192.dp, 48.dp) // Adjust the size to fit your layout
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

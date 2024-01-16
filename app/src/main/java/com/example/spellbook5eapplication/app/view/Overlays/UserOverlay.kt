import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.UserData
import kotlinx.coroutines.flow.StateFlow

@Composable
fun UserOverlay(
    signInViewModel: SignInViewModel,
    onDismissRequest: () -> Unit
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
                SignOutButton(signInViewModel)
            }
        } else {
            SignInButton(signInViewModel, signInResultLauncher)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onDismissRequest) {
            Text("Dismiss")
        }
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
            userData.profilePictureUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = userData.name ?: "", fontWeight = FontWeight.Bold)
            Text(text = userData.userId)
        }
    }
}

@Composable
fun SignInButton(signInViewModel: SignInViewModel, signInResultLauncher: ActivityResultLauncher<Intent>) {
    Button(onClick = {
        signInViewModel.signInWithGoogle { intentSenderRequest ->
            signInResultLauncher.launch(intentSenderRequest)
        }
    }) {
        Text("Sign In with Google")
    }
}

@Composable
fun SignOutButton(signInViewModel: SignInViewModel) {
    Button(onClick = { signInViewModel.onSignOutClick() }) {
        Text("Sign Out")
    }
}

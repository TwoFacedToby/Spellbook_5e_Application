import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import com.example.spellbook5eapplication.app.Model.Data_Model.SignInResult
import com.example.spellbook5eapplication.app.view.AuthUI.GoogleAuthUIClient
import com.example.spellbook5eapplication.app.view.AuthUI.SignInIntentSender
import com.example.spellbook5eapplication.app.view.AuthUI.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val googleAuthUIClient: GoogleAuthUIClient // Inject GoogleAuthUIClient
): ViewModel() {

    var signInIntentSender: SignInIntentSender? = null
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    // Function to initiate Google Sign-In
    fun signInWithGoogle(onIntentReady: (IntentSenderRequest) -> Unit) {
        viewModelScope.launch {
            try {
                val signInIntentSender = googleAuthUIClient.signIn()
                signInIntentSender?.let {
                    val intentSenderRequest = IntentSenderRequest.Builder(it).build()
                    onIntentReady(intentSenderRequest)
                }
            } catch (e: Exception) {
                _state.update { it.copy(isSignInSuccessful = false, signInError = e.message) }
            }
        }
    }

    // Function to process the result of the sign-in attempt
    fun onSignInResult(intent: Intent) {
        viewModelScope.launch {
            try {
                val signInResult: SignInResult = googleAuthUIClient.getSignInResultFromIntend(intent)
                _state.update {
                    it.copy(
                        isSignInSuccessful = signInResult.data != null,
                        signInError = signInResult.error
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isSignInSuccessful = false, signInError = e.message)
                }
            }
        }
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            googleAuthUIClient.signOut()
        }
    }

    fun resetSignInState() {
        _state.update {
            it.copy(isSignInSuccessful = false, signInError = null)
        }
    }

}

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.content.IntentSender
import com.example.spellbook5eapplication.app.Model.Data_Model.SignInResult
import com.example.spellbook5eapplication.app.view.AuthUI.GoogleAuthUIClient
import com.example.spellbook5eapplication.app.view.AuthUI.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val googleAuthUIClient: GoogleAuthUIClient // Inject GoogleAuthUIClient
): ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    // Function to initiate Google Sign-In
    fun signInWithGoogle() {
        viewModelScope.launch {
            try {
                val signInIntentSender = googleAuthUIClient.signIn()
                signInIntentSender?.let {
                    // Handle the IntentSender, typically by starting an activity for result
                    // You'll need to pass this IntentSender back to the UI
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isSignInSuccessful = false, signInError = e.message)
                }
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

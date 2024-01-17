import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.IntentSenderRequest
import com.example.spellbook5eapplication.app.Model.Data_Model.SignInResult
import com.example.spellbook5eapplication.app.Model.Data_Model.UserData
import com.example.spellbook5eapplication.app.Utility.GlobalLogInState
import com.example.spellbook5eapplication.app.Utility.SignInEvent
import com.example.spellbook5eapplication.app.view.AuthUI.SignInIntentSender
import com.example.spellbook5eapplication.app.view.AuthUI.SignInState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel(
    private val googleAuthUIClient: GoogleAuthUIClient // Inject GoogleAuthUIClient
): ViewModel() {

    var signInIntentSender: SignInIntentSender? = null
    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()
    private val _eventFlow = MutableSharedFlow<SignInEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _userData = MutableStateFlow<UserData?>(null)
    val userData: StateFlow<UserData?> = _userData.asStateFlow()

    // Function to initiate Google Sign-In
    fun signInWithGoogle(onIntentReady: (Intent) -> Unit) {
        val signInIntent = googleAuthUIClient.signIn()
        onIntentReady(signInIntent)
    }

    // Function to process the result of the sign-in attempt
    fun onSignInResult(intent: Intent) {
        viewModelScope.launch {
            try {
                val signInResult: SignInResult = googleAuthUIClient.handleSignInResult(intent)
                _state.update {
                    it.copy(
                        isSignInSuccessful = signInResult.data != null,
                        signInError = signInResult.error,
                        data = signInResult.data
                    )
                }
                Log.d("LogDataSignInResult", "signInResult $signInResult.data")
                if (signInResult.data != null) {
                    _eventFlow.emit(SignInEvent.SignInSuccess)
                    _eventFlow.emit(SignInEvent.DismissOverlay)
                    GlobalLogInState.setLoggedInState(signInResult.data.userId, signInResult.data.name!!, signInResult.data.profilePictureUrl!!)
                } else {
                    _eventFlow.emit(SignInEvent.SignInFailure)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isSignInSuccessful = false, signInError = e.message)
                }
                _eventFlow.emit(SignInEvent.SignInFailure)
            }
        }
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            googleAuthUIClient.signOut()
            _state.update { SignInState(isSignInSuccessful = false, signInError = null) }
            _eventFlow.emit(SignInEvent.SignOutSuccess)
            GlobalLogInState.reset()
        }
    }

    private fun setUserProfile(userData: UserData) {
        _userData.value = userData
    }

    private fun clearUserProfile() {
        _userData.value = null
    }

    fun resetSignInState() {
        _state.update {
            it.copy(isSignInSuccessful = false, signInError = null)
        }
    }

}

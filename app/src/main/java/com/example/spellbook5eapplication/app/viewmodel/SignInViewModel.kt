import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.SignInResult
import com.example.spellbook5eapplication.app.Model.Data_Model.UserData
import com.example.spellbook5eapplication.app.Utility.GlobalLogInState
import com.example.spellbook5eapplication.app.Utility.SignInEvent
import com.example.spellbook5eapplication.app.view.AuthUI.SignInIntentSender
import com.example.spellbook5eapplication.app.view.AuthUI.SignInState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInViewModel(
    private val googleAuthUIClient: GoogleAuthUIClient // Inject GoogleAuthUIClient
): ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth



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

    fun createAccountWithEmail(email: String, password: String, name: String) {
        viewModelScope.launch {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()

                val user = authResult.user

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName("User Display Name") // Replace with actual name if available
                    .build()

                user?.updateProfile(profileUpdates)?.await()

                signInEmail(email, password)

            } catch (e: Exception) {

                _eventFlow.emit(SignInEvent.SignInFailure)

            }
        }
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
                if (signInResult.data != null) {
                    _eventFlow.emit(SignInEvent.SignInSuccess)
                    _eventFlow.emit(SignInEvent.DismissOverlay)
                    GlobalLogInState.setLoggedInState(false, signInResult.data.userId, signInResult.data.name!!, signInResult.data.profilePictureUrl)
                } else {
                    _eventFlow.emit(SignInEvent.SignInFailure)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(isSignInSuccessful = false, signInError = e.message)
                }
                _eventFlow.emit(SignInEvent.CreateAccountFailed)
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

    fun getSignedInUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun signInEmail(username: String, password: String) {
        viewModelScope.launch {
            val signInResult = googleAuthUIClient.signInEmail(username, password)
            _state.update {
                it.copy(
                    isSignInSuccessful = signInResult.data != null,
                    signInError = signInResult.error,
                    data = signInResult.data
                )
            }
            if (signInResult.data != null) {
                _eventFlow.emit(SignInEvent.SignInSuccess)
                _eventFlow.emit(SignInEvent.DismissOverlay)
            } else {
                _eventFlow.emit(SignInEvent.SignInFailure)
            }
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

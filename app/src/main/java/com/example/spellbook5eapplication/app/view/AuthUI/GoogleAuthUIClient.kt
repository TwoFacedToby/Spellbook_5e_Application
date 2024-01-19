import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.SignInResult
import com.example.spellbook5eapplication.app.Model.Data_Model.UserData
import com.example.spellbook5eapplication.app.Utility.GlobalLogInState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await


class GoogleAuthUIClient(private val context: Context) {
    private val auth: FirebaseAuth = Firebase.auth
    private lateinit var googleSignInClient: GoogleSignInClient

    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    suspend fun signInEmail(email: String, password: String): SignInResult {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user

            val uid = user?.uid ?: ""
            val displayName = user?.displayName ?: "John Doe"
            val photoURL = user?.photoUrl?.toString() ?: ""

            GlobalLogInState.setLoggedInState(
                simpleLogin = true,
                userId = uid,
                userName = displayName,
                userPhotoUrl = photoURL
            )
            SignInResult(
                data = UserData(
                    userId = uid,
                    name = displayName,
                    profilePictureUrl = photoURL
                ),
                error = null
            )
        } catch (exception: Exception) {
            Log.w("emailLogin", "signInWithEmail:failure", exception)
            SignInResult(data = null, error = exception.localizedMessage)
        }
    }


    fun signIn(): Intent {
        return googleSignInClient.signInIntent
    }

    suspend fun handleSignInResult(data: Intent?): SignInResult {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        return try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            val user = authResult.user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        name = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                error = null
            )
        } catch (e: ApiException) {
            // Handle API exception here
            SignInResult(data = null, error = e.localizedMessage)
        }
    }

    // Function to sign out the user
    fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            auth.signOut()
        }
    }

    fun getSignedInUser(): UserData? {
        return auth.currentUser?.run {
            UserData(
                userId = uid,
                name = displayName,
                profilePictureUrl = photoUrl?.toString()
            )
        }
    }
}
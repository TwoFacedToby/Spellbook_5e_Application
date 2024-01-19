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

    fun signInEmail(email : String, password : String) {
        Log.d("emailLogin", "Email: $email, Password: $password")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("emailLogin", "signInWithEmail:success")
                    val user = FirebaseAuth.getInstance().currentUser
                    var uid = ""
                    var displayName = "John Doe"
                    var photoURL = ""
                    if(user?.uid != null) {
                        uid = user?.uid!!
                    }
                    if (user?.displayName != null) {
                        displayName = user?.displayName!!
                    }
                    if(user?.photoUrl != null) {
                        photoURL = user?.photoUrl.toString()
                    }
                        GlobalLogInState.setLoggedInState(
                            uid,
                            displayName,
                            photoURL
                        )
                    } else {
                    Log.w("emailLogin", "signInWithEmail:failure", task.exception)
            }
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

    // Function to get the currently signed in user
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
package com.example.spellbook5eapplication.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState = Channel<AuthState>()
    val authState = _authState.receiveAsFlow()

    // Function to handle user sign-in
    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                _authState.send(AuthState.Success)
            } catch (e: Exception) {
                _authState.send(AuthState.Error(e.message))
            }
        }
    }

    // Function to handle user sign-up
    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                _authState.send(AuthState.Success)
            } catch (e: Exception) {
                _authState.send(AuthState.Error(e.message))
            }
        }
    }

    suspend fun signOut() {
        firebaseAuth.signOut()
        _authState.send(AuthState.SignedOut)
    }

    sealed class AuthState {
        object Success : AuthState()
        object SignedOut : AuthState()
        class Error(val message: String?) : AuthState()
    }
}

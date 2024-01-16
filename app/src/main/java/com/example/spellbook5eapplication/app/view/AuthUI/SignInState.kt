package com.example.spellbook5eapplication.app.view.AuthUI

import com.example.spellbook5eapplication.app.Model.Data_Model.UserData

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val data: UserData? = null
)

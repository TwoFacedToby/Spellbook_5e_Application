package com.example.spellbook5eapplication.app.Utility

sealed class SignInEvent {
    object SignInSuccess : SignInEvent()
    object SignInFailure : SignInEvent()
    object SignOutSuccess : SignInEvent()
    object DismissOverlay : SignInEvent()
    object CreateAccountFailed : SignInEvent()
    object CreateAccountFailedAlternative : SignInEvent()
}
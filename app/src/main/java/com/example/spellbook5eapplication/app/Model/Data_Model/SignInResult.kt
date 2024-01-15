package com.example.spellbook5eapplication.app.Model.Data_Model

data class SignInResult(
    val data: UserData?,
    val error: String?
)

data class UserData(
    val userId: String,
    val name: String?,
    val profilePictureUrl: String?
)
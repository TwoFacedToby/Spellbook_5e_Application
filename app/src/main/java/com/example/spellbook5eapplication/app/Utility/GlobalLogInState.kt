package com.example.spellbook5eapplication.app.Utility

object GlobalLogInState {
    var isloggedIn = false
    var userId = ""
    var userName = ""
    var userPhotoUrl = ""

    fun reset() {
        isloggedIn = false
        userId = ""
        userName = ""
        userPhotoUrl = ""
    }

    fun setLoggedInState(userId: String, userName: String, userPhotoUrl: String) {
        isloggedIn = true
        this.userId = userId
        this.userName = userName
        this.userPhotoUrl = userPhotoUrl
    }
}
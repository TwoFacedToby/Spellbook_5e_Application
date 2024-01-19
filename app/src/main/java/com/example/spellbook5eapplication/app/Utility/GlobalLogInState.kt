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

    fun setLoggedInState(userId: String, userName: String, userPhotoUrl: String?) {
        isloggedIn = true
        if(userId != null){
            this.userId = userId
        } else {
            this.userId = ""
        }
        if(userName != null){
            this.userName = userName
        } else {
            this.userName = ""
        }
        if (userPhotoUrl == null) {
            this.userPhotoUrl = ""
        } else {
            this.userPhotoUrl = userPhotoUrl
        }
    }
}
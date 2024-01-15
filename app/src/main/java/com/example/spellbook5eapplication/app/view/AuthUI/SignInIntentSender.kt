package com.example.spellbook5eapplication.app.view.AuthUI

import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest

interface SignInIntentSender {
    fun sendIntent(intentSender: IntentSenderRequest)
}
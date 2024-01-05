package com.example.spellbook5eapplication.app.Utility

import androidx.compose.runtime.Composable

interface Displayable {
    fun renderCardComposable(
        displayable: Displayable
    ): @Composable () -> Unit
}
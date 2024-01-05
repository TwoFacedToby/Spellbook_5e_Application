package com.example.spellbook5eapplication.app.Utility

import androidx.compose.runtime.Composable
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info

interface Displayable {

    fun renderComposable(
        onFullSpellCardRequest: () -> Unit,
        onAddToSpellbookRequest: (Spell_Info.SpellInfo) -> Unit
    ): @Composable () -> Unit
}
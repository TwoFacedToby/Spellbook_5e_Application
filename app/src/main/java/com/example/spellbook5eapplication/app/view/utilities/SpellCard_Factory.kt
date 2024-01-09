package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.runtime.Composable
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

interface SpellCardFactory {
    fun createSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit
    fun createLargeSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit
}

class DefaultSpellCardFactory(): SpellCardFactory {
    override fun createSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit {

        return when (displayable) {
            is Spell_Info.SpellInfo -> {
                { SpellCard(
                    spell = displayable
                ) }
            }
            else -> { {} } // Return an empty composable for unrecognized types
        }
    }

    override fun createLargeSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit {

        return when (displayable) {
            is Spell_Info.SpellInfo -> {
                { LargeSpellCardOverlay(
                    spell = displayable
                ) }
            }
            else -> { {} }
        }
    }
}

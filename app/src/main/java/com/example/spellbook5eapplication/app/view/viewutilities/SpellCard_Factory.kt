package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.runtime.Composable
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCard
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard

interface SpellCardFactory {
    fun createSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit
}

class DefaultSpellCardFactory : SpellCardFactory {
    override fun createSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit {

        return when (displayable) {
            is Spell.SpellInfo -> {
                { SpellCard(
                    spell = displayable
                ) }
            }
            else -> { {} } // Return an empty composable for unrecognized types
        }
    }
}

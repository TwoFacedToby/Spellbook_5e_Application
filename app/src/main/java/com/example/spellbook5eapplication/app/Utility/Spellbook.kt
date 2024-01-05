package com.example.spellbook5eapplication.app.Model

import androidx.compose.runtime.Composable
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.spellCards.SpellbookCard

class Spellbook(var spellbookName: String): Displayable {
    val spells: MutableList<String> = mutableListOf()


    fun addSpellToSpellbook(spellName: String) {
        if (!spells.contains(spellName)) {
            spells.add(spellName)
        }
    }


    fun removeSpell(spellName: String) {
        spells.remove(spellName)
    }



    override fun toString(): String {
        return "Spellbook(spellbookName='$spellbookName', spells=$spells)"
    }



    override fun renderComposable(
        onFullSpellCardRequest: () -> Unit,
        onAddToSpellbookRequest: (Spell_Info.SpellInfo) -> Unit
    ): @Composable () -> Unit {
        return {
            // The parameters are not used for Spellbook
            SpellbookCard(spellbook = this)
        }
    }


}

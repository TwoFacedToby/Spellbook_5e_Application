package com.example.spellbook5eapplication.app.Model.Data_Model

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.spellCards.SpellbookCard

class Spellbook(var spellbookName: String, var imageIdentifier: String = "spellbook_brown"): Displayable {

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

    override fun renderCardComposable(spellBook: Displayable): @Composable () -> Unit {
        return {
            // Check if the Displayable is actually a Spellbook and render accordingly
            if (spellBook is Spellbook) {
                // Render SpellbookCard if it's a Spellbook
                SpellbookCard(spellbook = spellBook)
            } else {
                Log.d("Errors","Error: Item is not a Spellbook")
            }

        }
    }
}

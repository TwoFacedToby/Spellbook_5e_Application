package com.example.spellbook5eapplication.app.view.utilities

import androidx.compose.runtime.Composable
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCard
import com.example.spellbook5eapplication.app.view.spellCards.StandardSpellCard
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

interface SpellCardFactory {
    fun createFromSpell(
        spell: Spell,
        isHomebrew : Boolean,
    )
    : @Composable () -> Unit
}

class StandardSpellCardFactory(
    private val addButtonFactory: ButtonFactory,
    private val favoriteButtonFactory: ButtonFactory
) : SpellCardFactory {
    override fun createFromSpell(
        spell: Spell,
        isHomebrew: Boolean
    ): @Composable () -> Unit {
        val addButtonComposable = addButtonFactory.createButton { onAddToSpellBookRequest(spell) }.render()
        val favoriteButtonComposable = favoriteButtonFactory.createButton { onFavoritesClicked(spell) }.render()

        return {
            StandardSpellCard(
                spell = spell,
                isHomeBrew = isHomebrew,
                onFullSpellCardRequest = { onFullSpellCardRequest(spell) },
                addButtonComposable = addButtonComposable,
                favoriteButtonComposable = favoriteButtonComposable
            )
        }
    }
}

class LargeSpellCardFactory(
    private val addButtonFactory: ButtonFactory,
    private val favoriteButtonFactory: ButtonFactory,
    private val closeButtonFactory: CloseButtonFactory,
    private val deleteButtonFactory: DeleteButtonFactory
) : SpellCardFactory {
    override fun createFromSpell(
        spell: Spell,
        isHomebrew: Boolean
    ): @Composable () -> Unit {
        val closeButtonComposable = closeButtonFactory.createButton { onDismissRequest() }.render()
        val addButtonComposable = addButtonFactory.createButton { onAddToSpellBookRequest(spell) }.render()
        val favoriteButtonComposable = favoriteButtonFactory.createButton { onFavoritesClicked(spell) }.render()
        val deleteButtonComposable = deleteButtonFactory.createButton { onDeleteClicked(spell) }.render()

        return {
            LargeSpellCard(
                isHomeBrew = isHomebrew,
                closeButtonComposable = closeButtonComposable,
                addButtonComposable = addButtonComposable,
                favoriteButtonComposable = favoriteButtonComposable,
                deleteButtonComposable = deleteButtonComposable,
                spell = spell
            )
        }
    }
}

fun onFullSpellCardRequest(spell: Spell) {
    GlobalOverlayState.setOverlaySpell(spell)
    GlobalOverlayState.showOverlay(OverlayType.LARGE_SPELLCARD)
}

fun onDismissRequest(){
    GlobalOverlayState.dismissOverlay()
}

fun onAddToSpellBookRequest(spell: Spell){
    GlobalOverlayState.setOverlaySpell(spell)
    GlobalOverlayState.showOverlay(OverlayType.ADD_TO_SPELLBOOK)
}

fun onFavoritesClicked(spell: Spell){

}

fun onDeleteClicked(spell: Spell){
    GlobalOverlayState.setOverlaySpell(spell)
    GlobalOverlayState.showOverlay(OverlayType.DELETE_PROMPT)
}
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

class DefaultSpellCardFactory(private val buttonFactory: ButtonFactory): SpellCardFactory {
    override fun createSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit {

        val addButtonComposable = buttonFactory.createAddButton { onAddAction() }
        val favoriteButtonComposable = buttonFactory.createFavoriteButton { onFavoriteAction() }

        return when (displayable) {
            is Spell_Info.SpellInfo -> {
                { SpellCard(
                    onAddToSpellbookRequest = {},
                    onFullSpellCardRequest = {},
                    addButton = addButtonComposable,
                    favoriteButton = favoriteButtonComposable,
                    onExpandAction = {},
                    spell = displayable
                ) }
            }
            else -> { {} } // Return an empty composable for unrecognized types
        }
    }

    override fun createLargeSpellCard(
        displayable: Displayable,
    ): @Composable () -> Unit {

        val addButtonComposable = buttonFactory.createAddButton { onAddAction() }
        val favoriteButtonComposable = buttonFactory.createFavoriteButton { onFavoriteAction() }
        val deleteButtonComposable = buttonFactory.createDeleteButton { onDeleteAction() }
        val closeButtonComposable = buttonFactory.createCloseButton { onCloseAction() }

        return when (displayable) {
            is Spell_Info.SpellInfo -> {
                { LargeSpellCardOverlay(
                    onDismissRequest = {},
                    addButton = addButtonComposable,
                    deleteButton = deleteButtonComposable,
                    favoriteButton = favoriteButtonComposable,
                    closeButton = closeButtonComposable,
                    spell = displayable
                ) }
            }
            else -> { {} }
        }
    }

    private fun onAddAction(){
        GlobalOverlayState.showOverlay(OverlayType.ADD_TO_SPELLBOOK)
    }

    private fun onFavoriteAction(){
        /*TODO*/
    }

    private fun onDeleteAction(){
        /*
        TODO
         */
    }

    private fun onCloseAction(){
        GlobalOverlayState.dismissOverlay()
    }

    private fun onExpandAction(){
        GlobalOverlayState.showOverlay(OverlayType.LARGE_SPELLCARD)
    }
}

package com.example.spellbook5eapplication.app.view.Overlays

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.view.utilities.AddButtonFactory
import com.example.spellbook5eapplication.app.view.utilities.FavoritesButtonFactory
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun OverlayManager() {
    val addButtonFactory = AddButtonFactory()
    val favoritesButtonFactory = FavoritesButtonFactory()
    //val spellCardFactory = LargeSpellCardFactory(addButtonFactory, favoritesButtonFactory)

    var filter by remember { mutableStateOf(Filter()) }

    for (overlayType in GlobalOverlayState.getOverlayStack()) {
        when (overlayType) {
            OverlayType.LARGE_SPELLCARD -> {
                GlobalOverlayState.getOverlaySpell()?.let {
                    //spellCardFactory.createFromSpell(it, false)()
                }
            }
            OverlayType.ADD_TO_SPELLBOOK -> {
                GlobalOverlayState.getOverlaySpell()?.let { spell ->
                    AddToSpellBookOverlay(
                        spell = spell,
                        onDismissRequest = { GlobalOverlayState.dismissOverlay() }
                    )
                }
            }
            OverlayType.FILTER -> {
                /*FiltersOverlay(
                    filter = filter,
                    onDismissRequest = { GlobalOverlayState.dismissOverlay() }
                )*/
            }
            OverlayType.MAKE_SPELL -> {
                NewSpellOverlay(
                    onDismissRequest = { /*TODO*/ },
                    onFilterSelected = { /*TODO*/} )
            }
            OverlayType.DELETE_PROMPT -> {

            }
            OverlayType.ERASE_PROMPT -> {

            }
            // ... other overlay types ...
            else -> Unit
        }
    }
}

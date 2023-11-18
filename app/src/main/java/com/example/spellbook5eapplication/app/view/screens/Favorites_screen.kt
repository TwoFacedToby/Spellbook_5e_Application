import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.view.Overlays.AddToSpellBookOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun FavoriteScreen(spellController: SpellController, spellListLoader: SpelllistLoader, globalOverlayState: GlobalOverlayState) {
    // State to keep track of the currently selected spell
    val nullSpell = Spell_Info.SpellInfo(null, "Example name", null , null, null, null , null, null, null , null, null, null , null, null, null , null, null, null , null, null, null, null , null, null, null, null , null, null, null, null , null, null)
    var overlaySpell by remember { mutableStateOf(nullSpell) }

    // Load the favourites SpellList
    val favouritesSpellList = remember { spellListLoader.loadFavouritesAsSpellList(spellController) }

    // Display the SpellList using SpellQuery
    SpellQuery(
        filter = null,
        spellList = favouritesSpellList,
        onFullSpellCardRequest = { spell ->
            overlaySpell = spell
            globalOverlayState.showOverlay(OverlayType.LARGE_SPELLCARD)
        },
        onAddToSpellbookRequest = { spell ->
            //TODO add functiionality
        }
    )

    // Iterate through the overlay stack and handle each overlay type
    for (overlayType in globalOverlayState.getOverlayStack()) {
        when (overlayType) {
            OverlayType.LARGE_SPELLCARD -> {
                LargeSpellCardOverlay(globalOverlayState, { globalOverlayState.dismissOverlay() }, overlaySpell)
            }
            OverlayType.ADD_TO_SPELLBOOK -> {
                CustomOverlay(
                    globalOverlayState = globalOverlayState,
                    overlayType = OverlayType.ADD_TO_SPELLBOOK,
                    onDismissRequest = { globalOverlayState.dismissOverlay() }
                ) {
                    AddToSpellBookOverlay(
                        onDismissRequest = { globalOverlayState.dismissOverlay() }
                    )
                }
            }
            OverlayType.FILTER -> {
                CustomOverlay(
                    globalOverlayState = globalOverlayState,
                    overlayType = OverlayType.FILTER,
                    onDismissRequest = { globalOverlayState.dismissOverlay() }
                ){
                    FiltersOverlay(onDismissRequest = { globalOverlayState.dismissOverlay() }, onFilterSelected = {/* TODO */})
                }
            }
            else -> Unit
        }
    }
}

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.view.Overlays.AddToSpellBookOverlay
import com.example.spellbook5eapplication.app.view.Overlays.FiltersOverlay
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardOverlay
import com.example.spellbook5eapplication.app.view.utilities.CustomOverlay
import com.example.spellbook5eapplication.app.view.utilities.FilterButton
import com.example.spellbook5eapplication.app.view.utilities.UserInputField
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import kotlinx.coroutines.runBlocking

@Composable
fun FavoriteScreen(spellController: SpellController, spellListLoader: SpelllistLoader, globalOverlayState: GlobalOverlayState) {
    // State to keep track of the currently selected spell
    /*val nullSpell = Spell_Info.SpellInfo(
        null,
        "Example name",
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
    var overlaySpell by remember { mutableStateOf(nullSpell) }

    // Load the favourites SpellList
    /*val favouritesSpellList =
        remember { spellListLoader.loadFavouritesAsSpellList() }*/
    val emptySpellList : SpellList = SpellList()

    var favouriteSpellList by remember { mutableStateOf(emptySpellList) }
    runBlocking {
        favouriteSpellList = spellListLoader.loadFavouritesAsSpellList()
    }


    var filter by remember { mutableStateOf(Filter())}
    println("Current filter: $filter")
    println("Current filter level size: " + filter.getLevel().size)

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.search_view_background),
                contentDescription = "Search view background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                alpha = 0.5F
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, top = 100.dp, end = 10.dp, bottom = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    UserInputField(
                        label = "Search",
                        singleLine = true,
                        onInputChanged = {
                                input ->
                            filter = updateFilterWithSearchName(filter, input)
                            println("User input: $input")
                        },
                        modifier = Modifier
                            .size(width = 220.dp, height = 48.dp),
                        imeAction = ImeAction.Search
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    FilterButton(
                        onShowFiltersRequest = {
                            globalOverlayState.showOverlay(
                                OverlayType.FILTER,
                            )
                        })
                }

                SpellQuery(
                    filter = filter,
                    //spellList = favouriteSpellList,
                    onFullSpellCardRequest = { spell ->
                        overlaySpell = spell
                        globalOverlayState.showOverlay(OverlayType.LARGE_SPELLCARD)
                    },
                    onAddToSpellbookRequest = {
                        overlaySpell = it
                        globalOverlayState.showOverlay(
                            OverlayType.ADD_TO_SPELLBOOK,
                        )
                    }
                )

                /*favouritesSpellListState.value?.let { favoritesSpellList ->
                    println("SpellQuery recomposing with filter: $filter")

                }*/
            /*{ spell ->
                    Favourites.addFavourite(spell.name ?: "")
                    CoroutineScope(Dispatchers.IO).launch {
                        Favourites.saveFavouritesAsSpellbook()
                    }
                }*/
            }
            // Iterate through the overlay stack and handle each overlay type
            for (overlayType in globalOverlayState.getOverlayStack()) {
                println("Current Overlay Type: $overlayType")
                when (overlayType) {
                    OverlayType.LARGE_SPELLCARD -> {
                        println("LARGE_SPELLCARD overlay triggered")
                        LargeSpellCardOverlay(
                            globalOverlayState,
                            { globalOverlayState.dismissOverlay() },
                            overlaySpell
                        )
                    }

                    OverlayType.ADD_TO_SPELLBOOK -> {
                        println("ADD_TO_SPELLBOOK overlay triggered")
                        CustomOverlay(
                            globalOverlayState = globalOverlayState,
                            overlayType = OverlayType.ADD_TO_SPELLBOOK,
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
                        ) {
                            AddToSpellBookOverlay(
                                spellInfo = overlaySpell,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            )
                        }
                    }

                    OverlayType.FILTER -> {
                        println("FILTER overlay triggered")
                        CustomOverlay(
                            globalOverlayState = globalOverlayState,
                            overlayType = OverlayType.FILTER,
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
                        ){
                            FiltersOverlay(
                                onDismissRequest = { globalOverlayState.dismissOverlay() },
                                currentfilter = filter,
                                createNewFilter = { Filter() },
                                updateFilterState = { newFilter ->
                                    filter = newFilter
                                    println("New filter applied: $newFilter") }
                            )
                        }
                    }

                    else -> println("Unhandled Overlay Type: $overlayType")
                }
            }
        }
    }*/
}

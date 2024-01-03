import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.SpellbookViewModel
import com.example.spellbook5eapplication.app.Utility.SpellbookViewModelFactory
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import com.example.spellbook5eapplication.app.view.spellCards.SpellQuery
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun SpellbooksScreen(
    spellController: SpellController,
    spellListLoader: SpelllistLoader,
    globalOverlayState: GlobalOverlayState
) {
    /*//Initializing viewModel to make the app recompose when a new spellbook is selected.
    val viewModel: SpellbookViewModel = viewModel(
        factory = SpellbookViewModelFactory(spellController, spellListLoader)
    )

    // State for dropdown expanded
    var expanded by remember { mutableStateOf(false) }
    val selectedSpellbook by viewModel.selectedSpellbook

    val spellbooks = viewModel.spellbooks

    val nullSpell = Spell_Info.SpellInfo(
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
    val spellList by viewModel.spellList

    var filter by remember { mutableStateOf(Filter())}

    // State for showing the dialog
    var showDialog by remember { mutableStateOf(false) }
    // State for the new spellbook name
    var newSpellbookName by remember { mutableStateOf("") }

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
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text("Create New Spellbook")
                }
                Button(onClick = { expanded = true},
                    modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = selectedSpellbook?.spellbookName ?: "Select a Spellbook"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    spellbooks.forEach { spellbook ->
                        DropdownMenuItem(onClick = {
                            viewModel.selectSpellbook(spellbook)
                            expanded = false
                        }) {
                            Text(text = spellbook.spellbookName)
                        }
                    }
                }

                SpellQuery(
                    filter = filter,
                    //spellList = spellList,
                    onFullSpellCardRequest = { spellInfo ->
                        overlaySpell = spellInfo
                        globalOverlayState.showOverlay(OverlayType.LARGE_SPELLCARD)
                    },
                    onAddToSpellbookRequest = { spellInfo ->
                        overlaySpell = spellInfo
                        globalOverlayState.showOverlay(OverlayType.ADD_TO_SPELLBOOK)
                    }
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Show the dialog when the state is true
            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    Column(
                        modifier = Modifier.background(Color.White).padding(16.dp)
                    ) {
                        Text("Enter the name of your new spellbook:")
                        TextField(
                            value = newSpellbookName,
                            onValueChange = { newSpellbookName = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                // Add the new spellbook
                                viewModel.createSpellbook(newSpellbookName)

                                showDialog = false
                                newSpellbookName = "" // Reset the name for the next use
                            },
                            modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
                        ) {
                            Text("Create")
                        }
                    }
                }
            }
        }
    }*/
}

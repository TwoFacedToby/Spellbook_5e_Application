package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.viewmodel.FilterViewModel
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType

@Composable
fun SearchFilterBar(
){
    val filterViewModel: FilterViewModel = viewModel()
    val inputText by filterViewModel.inputText

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    )
    {
        UserInputField(
            label = "Search",
            onInputChanged = { input ->
                filterViewModel.updateFilterWithSearchName(input)
            },
            modifier = Modifier.size(height = 48.dp, width = 220.dp),
            singleLine = true,
            imeAction = ImeAction.Search,
            initialInput = inputText,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.width(5.dp))
        FilterButton(
            onShowFiltersRequest = {
                GlobalOverlayState.showOverlay(
                    OverlayType.FILTER,
                )
            },
            filtersCount = filterViewModel.filterNumber.intValue
        )
    }
}
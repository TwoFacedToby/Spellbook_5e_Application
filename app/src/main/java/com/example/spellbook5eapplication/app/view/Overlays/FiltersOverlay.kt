package com.example.spellbook5eapplication.app.view.Overlays

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.viewmodel.FilterViewModel

@Composable
fun FiltersOverlay(
    filterViewModel: FilterViewModel,
    onDismissRequest: () -> Unit,
) {
    val spellLevel by filterViewModel.selectedLevels.observeAsState(listOf())
    val isConcentrationSelected by filterViewModel.isConcentrationSelected.observeAsState(false)
    val isRitualSelected by filterViewModel.isRitualSelected.observeAsState(false)
    val selectedComponents by filterViewModel.selectedComponents.observeAsState(listOf())
    val selectedSaves by filterViewModel.selectedSaves.observeAsState(listOf())
    val selectedClasses by filterViewModel.selectedClasses.observeAsState(listOf())

    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .clickable { onDismissRequest() },
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.overlay_box_color),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp))
            {
                item {Spacer(modifier = Modifier.height(5.dp))}
                item {Text(
                    text = "Spell Level",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )}
                    filterViewModel.spellLevels.chunked(5).forEach { rowLevels ->
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowLevels.forEach { level ->
                                    FilterButton(
                                        modifier = Modifier.size(55.dp),
                                        contentPaddingValues = PaddingValues(1.dp),
                                        label = level.toString(),
                                        isSelected = rememberIsLevelSelected(
                                            filterViewModel = filterViewModel,
                                            level = level
                                        ),
                                        onToggleSelection = {
                                            filterViewModel.toggleLevelSelection(level)
                                        },
                                    )
                                }
                            }
                        }
                    }
                item { Spacer(modifier = Modifier.height(5.dp)) }
                item {Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Concentration",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            FilterButton(
                                modifier = Modifier.size(46.dp),
                                contentPaddingValues = PaddingValues(1.dp),
                                isSelected = isConcentrationSelected,
                                label = "Yes",
                                onToggleSelection = { filterViewModel.toggleConcentrationSelection() }
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            FilterButton(
                                modifier = Modifier.size(46.dp),
                                contentPaddingValues = PaddingValues(1.dp),
                                isSelected = !isConcentrationSelected,
                                label = "No",
                                onToggleSelection = { filterViewModel.toggleConcentrationSelection() }
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Ritual",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            FilterButton(
                                modifier = Modifier.size(46.dp),
                                contentPaddingValues = PaddingValues(1.dp),
                                isSelected = isRitualSelected,
                                label = "Yes",
                                onToggleSelection = { filterViewModel.toggleRitualSelection() }
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            FilterButton(
                                modifier = Modifier.size(46.dp),
                                contentPaddingValues = PaddingValues(1.dp),
                                isSelected = !isRitualSelected,
                                label = "No",
                                onToggleSelection = { filterViewModel.toggleRitualSelection() }
                            )
                        }
                    }
                }
                }
                item { Spacer(modifier = Modifier.height(5.dp))}
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        Text(
                            text = "Components",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            filterViewModel.components.forEach() { components ->
                                FilterButton(
                                    modifier = Modifier.size(width = 100.dp, height = 40.dp),
                                    contentPaddingValues = PaddingValues(1.dp),
                                    label = components,
                                    isSelected = rememberIsComponentSelected(
                                        filterViewModel = filterViewModel,
                                        component = components
                                    ),
                                    onToggleSelection = {filterViewModel.toggleComponentSelection(components)}
                                )
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(5.dp)) }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Save Required",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        filterViewModel.saves.chunked(3).forEach { rowLevels ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowLevels.forEach { level ->
                                    FilterButton(
                                        modifier = Modifier.size(width = 100.dp, height = 40.dp),
                                        label = level,
                                        contentPaddingValues = PaddingValues(1.dp),
                                        isSelected = rememberIsSaveSelected(
                                            filterViewModel = filterViewModel,
                                            save = level
                                        ),
                                        onToggleSelection = { filterViewModel.toggleSaveSelection(level) }
                                    )
                                }
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(5.dp)) }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Spell list",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        filterViewModel.classes.chunked(2).forEach { rowLevels ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                rowLevels.forEach { level ->
                                    FilterButton(
                                        modifier = Modifier.size(width = 145.dp, height = 40.dp),
                                        label = level,
                                        isSelected = rememberIsClassSelected(
                                            filterViewModel = filterViewModel,
                                            classes = level
                                        ),
                                        onToggleSelection = { filterViewModel.toggleClassSelection(level) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    modifier: Modifier,
    contentPaddingValues: PaddingValues? = null,
    label: String,
    isSelected: Boolean,
    onToggleSelection: () -> Unit) {
    if(contentPaddingValues == null) {
        Button(
            modifier = modifier,
            onClick = onToggleSelection,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) colorResource(id = R.color.selected_button)
                else colorResource(id = R.color.unselected_button)
            ),
            border = BorderStroke(
                width = 2.dp,
                color = colorResource(id = R.color.border_color)
            ),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    } else {
        Button(
            modifier = modifier,
            onClick = onToggleSelection,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) colorResource(id = R.color.selected_button)
                else colorResource(id = R.color.unselected_button)
            ),
            border = BorderStroke(
                width = 2.dp,
                color = colorResource(id = R.color.border_color)
            ),
            contentPadding = contentPaddingValues,
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun rememberIsLevelSelected(filterViewModel: FilterViewModel, level: Int): Boolean {
    val selectedLevels by filterViewModel.selectedLevels.observeAsState(initial = emptyList())
    return selectedLevels.contains(level)
}

@Composable
fun rememberIsComponentSelected(filterViewModel: FilterViewModel, component: String): Boolean {
    val selectedComponents by filterViewModel.selectedComponents.observeAsState(initial = emptyList())
    return selectedComponents.contains(component)
}

@Composable
fun rememberIsSaveSelected(filterViewModel: FilterViewModel, save: String): Boolean {
    val selectedSaves by filterViewModel.selectedComponents.observeAsState(initial = emptyList())
    return selectedSaves.contains(save)
}

@Composable
fun rememberIsClassSelected(filterViewModel: FilterViewModel, classes: String): Boolean {
    val selectedClasses by filterViewModel.selectedComponents.observeAsState(initial = emptyList())
    return selectedClasses.contains(classes)
}



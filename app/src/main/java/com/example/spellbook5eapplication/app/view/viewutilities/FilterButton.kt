package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R

@Composable
fun FilterButton(onShowFiltersRequest: () -> Unit) {
    IconButton(
        onClick = { onShowFiltersRequest() },
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(2.dp)
            )
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(2.dp)
            ),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
    {
        Icon(
            painter = painterResource(id = R.drawable.filter),
            contentDescription = "Filter",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

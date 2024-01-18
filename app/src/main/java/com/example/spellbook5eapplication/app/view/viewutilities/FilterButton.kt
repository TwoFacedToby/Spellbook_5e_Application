package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R

@Composable
fun FilterButton(
    onShowFiltersRequest: () -> Unit,
    filtersCount: Int,
) {
    Box {
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
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        if (filtersCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 8.dp, y = (-4).dp)
                    .size(16.dp)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    drawCircle(color = Color.Red.copy(alpha = 0.8F))
                }
                Text(
                    text = filtersCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

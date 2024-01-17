package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState
import com.example.spellbook5eapplication.app.viewmodel.OverlayType
import com.example.spellbook5eapplication.app.viewmodel.QuickPlayViewModel

@Composable
fun QuickPlaySpellCard(spell: Spell.SpellInfo) {
    val quickPlayViewModel: QuickPlayViewModel = viewModel()
    val cardColor = MaterialTheme.colorScheme.secondary
    val images = SpellCardCreation(spell)
    Card(elevation = 10.dp,
        shape = MaterialTheme.shapes.small,
        backgroundColor = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                quickPlayViewModel.setPreventReset(true)
                GlobalOverlayState.currentSpell = spell
                GlobalOverlayState.showOverlay(OverlayType.LARGE_QUICKSPELLCARD)
            }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1F)
                ) {
                    Image(
                        painter = painterResource(id = images.schoolID),
                        contentDescription = "Spell school",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .border(
                                0.5.dp,
                                MaterialTheme.colorScheme.secondaryContainer,
                                shape = RoundedCornerShape(2.dp)
                            )
                    )
                }
                Box(
                    modifier = Modifier.weight(4F), contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = spell.name ?: "UNIDENTIFIED",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        maxLines = 1,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}
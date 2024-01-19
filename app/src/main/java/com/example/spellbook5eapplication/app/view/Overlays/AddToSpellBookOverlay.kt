package com.example.spellbook5eapplication.app.view.Overlays

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.view.viewutilities.OverlayBox
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState

@Composable
fun Add_to_spellbook(
    spellbooks: List<Spellbook>,
    spell: Spell.SpellInfo
) {
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
                .clip(shape = RoundedCornerShape(5.dp)),
            color = Color.Black.copy(alpha = 0.2F),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Add Spell To Spellbook",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        OverlayBox {

            items(spellbooks.size) { index ->
                val spellbook = spellbooks[index]
                if (!spellbook.spells.contains(spell.index)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = spellbook.spellbookName,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        IconButton(onClick = {
                            spellbook.addSpellToSpellbook(spell.index ?: "")
                            SpellbookManager.saveSpellbookToFile(spellbook.spellbookName)
                            LocalDataLoader
                                .getContext()
                                ?.get()
                                ?.let { context ->
                                    Toast
                                        .makeText(
                                            context,
                                            "${spell.name} added to ${spellbook.spellbookName}",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            GlobalOverlayState.dismissOverlay()
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Add,
                                contentDescription = "Add to spellbook",
                                tint = MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
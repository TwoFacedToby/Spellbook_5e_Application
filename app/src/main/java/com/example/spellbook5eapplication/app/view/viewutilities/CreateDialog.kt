package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.ui.theme.ButtonColors

@Composable
fun CreateDialog(
    onDismissRequest: () -> Unit
) {
    var newSpellbookName by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { /*TODO*/ }
    )
    {
        Box(
            modifier = Modifier
                .size(width = 250.dp, height = 150.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = "Create new Spellbook",
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Bold
                )
                UserInputField(
                    label = "Spellbook name",
                    onInputChanged = { input -> newSpellbookName = input },
                    modifier = Modifier
                        .height(40.dp)
                        .width(200.dp),
                    singleLine = true,
                    imeAction = ImeAction.Done,
                    initialInput = ""
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ColouredButton(
                        label = "Cancel",
                        modifier = Modifier,
                        color = ButtonDefaults.buttonColors(containerColor = ButtonColors.RedButton),
                        onClick = { onDismissRequest() })
                    ColouredButton(
                        label = "Create",
                        modifier = Modifier,
                        color = ButtonDefaults.buttonColors(containerColor = ButtonColors.GreenButton),
                        onClick = {
                            val newSpellbook = Spellbook(newSpellbookName)
                            SpellbookManager.addSpellbook(newSpellbook)
                            SpellbookManager.saveSpellbookToFile(newSpellbook.spellbookName)
                            onDismissRequest()
                        })
                }
            }
        }
    }
}
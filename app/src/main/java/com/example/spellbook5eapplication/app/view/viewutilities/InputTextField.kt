package com.example.spellbook5eapplication.app.view.viewutilities

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserInputField(
    label: String,
    onInputChanged: (String) -> Unit,
    modifier: Modifier,
    singleLine: Boolean,
    imeAction: ImeAction,
    initialInput: String,
) {
    var input by remember { mutableStateOf(initialInput) }

    val focusRequester = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    var alignment = Alignment.CenterVertically
    var otherModifier = Modifier
        .fillMaxSize()
        .padding(start = 5.dp)

    if (!singleLine) {
        alignment = Alignment.Top
        otherModifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
            .padding(start = 5.dp)
    }

    KeyboardVisibilityDetector { isVisible ->
        if (!isVisible) {
            focusManager.clearFocus()
        }

    }

    BasicTextField(
        value = input,
        onValueChange = {
            input = it
            onInputChanged(it)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),

        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    keyboardController?.show()
                }
            },
        singleLine = singleLine,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onPrimary),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = otherModifier,
                    verticalAlignment = alignment
                )
                {
                    Spacer(modifier = Modifier.size(4.dp, 20.dp))
                    if (input.isEmpty())
                        Text(
                            text = label,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )

                    Spacer(modifier = Modifier.size(4.dp, 20.dp))
                    innerTextField()
                }
            }
        }
    )
}




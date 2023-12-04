package com.example.bookreader.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextFieldWithDropDown(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit) = {},
    textFieldText: String,
    dropdownItemText: (Int) -> String?,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    isLoading: Boolean,
    list: List<Any>,
    onSelectIndex: (Int) -> Unit = {},
){
    var state by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    OutlinedTextField(
        leadingIcon = leadingIcon,
        value = TextFieldValue(
            text  = textFieldText
        ),
        readOnly = true,
        isError = isError,
        enabled = isEnabled,
        onValueChange = {},
        singleLine = true,
        trailingIcon = {
            when{
                isLoading -> {
                    CircularProgressIndicator()
                }
                !isLoading -> {
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            state = !state
                        }
                    )
                }
            }
        },
        modifier = modifier,
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            state = !state
                        }
                    }
                }
            }
    )

    if(state) {
        Box {
            DropdownMenu(
                expanded = true,
                onDismissRequest = {
                    state = false
                },
                modifier = Modifier
            ) {
                list.indices.forEach{
                    DropdownMenuItem(
                        leadingIcon = leadingIcon,
                        text = {
                            Text(
                                text = dropdownItemText(it) ?: list[it].toString(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        onClick = {
                            onSelectIndex(it)
                            state != state
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}
package com.example.bookreader.presentation.BookInfoScreen.dialog

import androidx.compose.runtime.MutableState

interface DialogController {
    val dialogTitle: MutableState<String>
    val openDialog: MutableState<Boolean>
    val warningText: MutableState<String>
    val showWarningText: MutableState<Boolean>
    fun onDialogEvent(event: DialogEvent)
}
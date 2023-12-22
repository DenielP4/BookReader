package com.example.bookreader.presentation.BookInfoScreen.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.bookreader.presentation.ui.theme.BlueLight
import com.example.bookreader.presentation.utils.Application

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarningDialog(
    dialogController: DialogController
) {
    if (dialogController.openDialog.value) {
        AlertDialog(
            onDismissRequest = { dialogController.onDialogEvent(DialogEvent.OnCancel) },
            title = null,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = dialogController.dialogTitle.value,
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            },
            backgroundColor = BlueLight,
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnAuth(Application.AUTHORIZATION))
                    }
                ) {
                    Text(
                        text = "АВТОРИЗАЦИЯ",
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnCancel)
                    }
                ) {
                    Text(
                        text = "ОТМЕНА",
                        color = Color.White
                    )
                }
            }
        )
    }
}
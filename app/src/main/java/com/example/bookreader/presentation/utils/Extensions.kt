package com.example.bookreader.presentation.utils

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.logging.SimpleFormatter

fun ViewModel.getCurrentTime(): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val cv = Calendar.getInstance().time
    return formatter.format(cv.time)
}
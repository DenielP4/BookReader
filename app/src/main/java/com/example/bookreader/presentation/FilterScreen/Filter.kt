package com.example.bookreader.presentation.FilterScreen

import java.io.Serializable

data class Filter(
    val bookName: String?,
    val bookAuthor: String?,
    val bookRating: List<Int>?
): Serializable

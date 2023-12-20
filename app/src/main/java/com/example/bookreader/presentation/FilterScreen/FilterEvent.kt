package com.example.bookreader.presentation.FilterScreen

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.bookreader.presentation.FilterScreen.resourses.RatingToggleInfo

sealed class FilterEvent {
    data class OnBookNameChange(val name: String): FilterEvent()
    data class OnBookAuthorChange(val author: String): FilterEvent()
    data class OnChangeAllRating(val ratingList: List<RatingToggleInfo>): FilterEvent()
    object OnChangeRating: FilterEvent()
    object OnConfirm: FilterEvent()
    object OnCancel: FilterEvent()
    object OnExit: FilterEvent()
    object OnDeleteName: FilterEvent()
    object OnDeleteAuthor: FilterEvent()
}

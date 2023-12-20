package com.example.bookreader.presentation.SearchBookScreen

import com.example.bookreader.presentation.FilterScreen.Filter

sealed class SearchBookEvent {
    data class OnBookClick(val route: String): SearchBookEvent()
    data class OnTextSearchChange(val text: String): SearchBookEvent()
    object OnClickDeleteSearchText: SearchBookEvent()
    data class OnShowFilterScreen(val route: String): SearchBookEvent()
    data class OnFilterGenre(val filter: String): SearchBookEvent()
    data class OnChangeFilter(val filter: Filter): SearchBookEvent()
}

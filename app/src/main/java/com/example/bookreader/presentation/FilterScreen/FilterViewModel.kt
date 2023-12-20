package com.example.bookreader.presentation.FilterScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.presentation.FilterScreen.resourses.RatingToggleInfo
import com.example.bookreader.presentation.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

class FilterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var filter = Filter(
        bookName = "",
        bookAuthor = "",
        bookRating = listOf()
    )

    var bookNameText = mutableStateOf("")
        private set
    var bookAuthorText = mutableStateOf("")
        private set
    var ratingListChange = mutableStateOf<List<Int>>(listOf())
        private set
    var ratingTab = mutableStateListOf(
        RatingToggleInfo(
            isChecked = false,
            rating = 5
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 4
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 3
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 2
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 1
        )
    )

    var ratingTabSave = mutableStateListOf(
        RatingToggleInfo(
            isChecked = false,
            rating = 5
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 4
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 3
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 2
        ),
        RatingToggleInfo(
            isChecked = false,
            rating = 1
        )
    )

    init {
//        filter.value = savedStateHandle.get<Filter>("filter")!!
//        Log.d("Мой фильтр из серча", "${filter.value}")
//        if (filter.value.bookName == "" && filter.value.bookAuthor == "" && filter.value.bookRating?.isEmpty() == true){
//            bookNameText.value = ""
//            bookAuthorText.value = ""
//            ratingTab = ratingTabSave
//        } else {
//            if (filter.value.bookName != ""){
//                bookNameText.value = filter.value.bookName!!
//            }
//            if (filter.value.bookAuthor != ""){
//                bookAuthorText.value = ""
//            }
//            if (filter.value.bookRating?.isEmpty() == false) {
//                ratingTab = ratingTabSave
//            }
//        }

    }


    var triState by mutableStateOf(ToggleableState.Indeterminate)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: FilterEvent) {
        when (event) {
            is FilterEvent.OnBookNameChange -> {
                bookNameText.value = event.name
            }

            is FilterEvent.OnBookAuthorChange -> {
                bookAuthorText.value = event.author
            }

            FilterEvent.OnCancel -> {
                bookNameText.value = ""
                bookAuthorText.value = ""
                ratingListChange.value = listOf()

                triState = ToggleableState.Indeterminate
                ratingTab.indices.forEach { index ->
                    ratingTab[index] = ratingTab[index].copy(
                        isChecked = triState == ToggleableState.On
                    )
                }
            }

            is FilterEvent.OnChangeAllRating -> {
                ratingListChange.value = event.ratingList.filter { it.isChecked }.map { it.rating }
                Log.d("РЕЙТИНГ NEw", "${ratingListChange.value}")
            }

            is FilterEvent.OnConfirm -> {
                filter = Filter(
                    bookName = bookNameText.value,
                    bookAuthor = bookAuthorText.value,
                    bookRating = ratingListChange.value
                )
                Log.d("Мой фильтр", "$filter")
                sendUiEvent(UiEvent.PopBackStack)
            }

            FilterEvent.OnDeleteName -> {
                bookNameText.value = ""
            }

            FilterEvent.OnDeleteAuthor -> {
                bookAuthorText.value = ""
            }

            is FilterEvent.OnChangeFilter -> {
                if (filter.bookName != "") {
                    bookNameText.value = filter.bookName!!
                    Log.d("bookNameText", "${bookNameText.value}")
                }
                if (filter.bookAuthor != "") {
                    bookAuthorText.value = filter.bookAuthor!!
                }
                if (filter.bookRating?.isEmpty() == false) {
                    ratingListChange.value = filter.bookRating!!
                    if (ratingListChange.value.contains(1) && ratingListChange.value.contains(2) && ratingListChange.value.contains(3) && ratingListChange.value.contains(4) && ratingListChange.value.contains(5)) {
                        triState = ToggleableState.On
                        ratingTab.indices.forEach { index ->
                            ratingTab[index] = ratingTab[index].copy(
                                isChecked = triState == ToggleableState.On
                            )
                        }
                    } else {
                        filter.bookRating?.forEachIndexed { index, rating ->
                            if (ratingListChange.value.contains(rating)) {
                                ratingTab[abs(rating-5)] = ratingTab[abs(rating-5)].copy(isChecked = true)
                            }
                        }

                    }


                }
            }

            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
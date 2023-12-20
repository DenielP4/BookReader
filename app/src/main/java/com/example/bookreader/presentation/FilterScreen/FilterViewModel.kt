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

class FilterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var filter = mutableStateOf(
        Filter(
            bookName = "",
            bookAuthor = "",
            bookRating = listOf()
        )
    )

    var bookNameText = mutableStateOf("")
        private set
    var bookAuthorText = mutableStateOf("")
        private set
    var ratingListChange = mutableStateOf<List<Int>>(listOf())
        private set

    init {
        filter.value = savedStateHandle.get<Filter>("filter")!!
        Log.d("Мой фильтр из серча", "${filter.value}")

    }

    var ratingTab = mutableStateListOf (
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

    var triState by mutableStateOf(ToggleableState.Indeterminate)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: FilterEvent) {
        when(event) {
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
                filter.value = Filter(
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

            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
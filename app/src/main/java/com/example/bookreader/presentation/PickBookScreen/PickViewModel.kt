package com.example.bookreader.presentation.PickBookScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Constants
import com.example.bookreader.common.Resource
import com.example.bookreader.domain.models.BookInfo
import com.example.bookreader.domain.models.UserBook
import com.example.bookreader.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickViewModel @Inject constructor(
    private val repository: BookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    var bookId: Int = -1
    var book: UserBook? = null
    var url = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        bookId = savedStateHandle.get<String>("bookId")?.toInt()!!
        Log.d("СЫЫЛКА НА КНИГУ", "${bookId}")
        loadBook()
    }

    fun loadBook() {
        isLoading.value = true
        viewModelScope.launch {
            val result = repository.getBookInfo(bookId)
            when (result) {
                is Resource.Success -> {
                    val bookEntries = result.data!!.file
                    url.value = Constants.BOOK_URL + bookEntries
                    isLoading.value = false
                }

                is Resource.Error -> {

                }

                else -> {}
            }
            Log.d("Loading", "${isLoading.value}")
        }
    }

}
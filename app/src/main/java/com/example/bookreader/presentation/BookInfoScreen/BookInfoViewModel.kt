package com.example.bookreader.presentation.BookInfoScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Constants
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.data.remote.responses.Review
import com.example.bookreader.domain.models.BookInfo
import com.example.bookreader.domain.models.BookList
import com.example.bookreader.domain.repository.BookRepository
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.domain.repository.UserRepository
import com.example.bookreader.presentation.AddReviewScreen.AddReviewEvent
import com.example.bookreader.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookInfoViewModel @Inject constructor(
    private val repository: BookRepository,
    private val repositoryUser: UserInfoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var book: BookInfo? = null
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var bookId: Int = -1

    var user: UserInfo? = null

    init {
        bookId = savedStateHandle.get<String>("bookId")?.toInt()!!
        loadBook()
    }

    fun onEvent(event: BookInfoEvent) {
        when(event) {
            BookInfoEvent.OnLoad -> {
                loadUser()
            }
        }
    }

    private fun loadUser() {
        isLoading.value = true
        loadError.value = ""
        viewModelScope.launch {
            val result = repositoryUser.getUser()
            when (result) {
                is Resource.Success -> {
                    loadError.value = ""
                    isLoading.value = false
                    user = result.data
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
            Log.d("Обновил информация", "$user")

        }

    }

    fun loadBook() {
        isLoading.value = true
        loadError.value = ""

        viewModelScope.launch {
            val result = repository.getBookInfo(bookId)
            when (result) {
                is Resource.Success -> {
                    val bookEntries = result.data!!
                    val url = Constants.IMAGE_URL + bookEntries.image
                    val currentBook = BookInfo(
                        name = bookEntries.name,
                        author = bookEntries.author,
                        rate = bookEntries.rate,
                        description = bookEntries.description,
                        image = url,
                        reviews = bookEntries.reviews,
                        id = bookEntries.id
                    )
                    loadError.value = ""
                    isLoading.value = false
                    book = currentBook
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
            Log.d("Book", "$book")
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
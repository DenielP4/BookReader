package com.example.bookreader.presentation.UserBookScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Constants.BOOK_URL
import com.example.bookreader.common.Constants.IMAGE_URL
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.data.remote.responses.Book
import com.example.bookreader.data.remote.responses.DeskOfBook
import com.example.bookreader.data.remote.responses.User
import com.example.bookreader.domain.models.UserBook
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.domain.repository.UserRepository
import com.example.bookreader.presentation.UserBookScreen.resourses.Menu
import com.example.bookreader.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserBookViewModel @Inject constructor(
    private val repository: UserInfoRepository,
    private val repositoryUser: UserRepository,
): ViewModel() {

    var user: UserInfo? = null

    var userOnServer: User? = null

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var loadUserBook = mutableStateOf(true)

    var shelf = mutableStateOf<List<DeskOfBook>>(listOf())
    var books = mutableStateOf<List<UserBook>>(listOf())
    var booksSave = mutableStateOf<List<UserBook>>(listOf())

    var topBook: UserBook? = null

    val sortDate = mutableStateOf(true)
    val sortAuthor = mutableStateOf(false)
    val sortAlfavit = mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val sortList = arrayOf(
        Menu(
            text = "ПО ДАТЕ ДОБАВЛЕНИЯ",
            isChecked = true
        ),
        Menu(
            text = "ПО НАЗВАНИЮ",
            isChecked = false
        ),
        Menu(
            text = "ПО АВТОРУ",
            isChecked = false
        )
    )

    var selectedText by mutableStateOf(sortList[0])


    fun onEvent(event: UserBookEvent) {
        when(event) {
            UserBookEvent.OnLoad -> {
                loadUser()
            }

            is UserBookEvent.OnClickAuth -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is UserBookEvent.OnClickBook -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is UserBookEvent.OnChangeSort -> {
                selectedText = event.sort
                sortList.indices.forEach { index ->
                    sortList[index] = sortList[index].copy(
                        isChecked = selectedText.text == sortList[index].text
                    )
                }
                if (selectedText.text == "ПО ДАТЕ ДОБАВЛЕНИЯ") {
                    books.value = booksSave.value
                }
                if (selectedText.text == "ПО НАЗВАНИЮ") {
                    books.value = booksSave.value.sortedBy { book ->
                        book.name
                    }
                }
                if (selectedText.text == "ПО АВТОРУ") {
                    books.value = booksSave.value.sortedBy { book ->
                        book.author
                    }
                }
            }
        }
    }

    private fun loadUser() {
        isLoading.value = true
        loadError.value = ""
        viewModelScope.launch {
            val result = repository.getUser()
            when (result) {
                is Resource.Success -> {
                    loadError.value = ""
                    isLoading.value = false
                    user = result.data
                    if (user!=null) {
                        loadUserOnServer(user?.userId!!)
                    }
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
            Log.d("Обновил полка", "$user")

        }
    }

    fun loadUserOnServer(userId: Int) {
        isLoading.value = true
        loadError.value = ""
        viewModelScope.launch {
            val result = repositoryUser.getUser(userId)
            when (result) {
                is Resource.Success -> {
                    loadError.value = ""
                    isLoading.value = false
                    userOnServer = result.data
                    shelf.value = userOnServer?.shelf!!
                    shelf.value.forEach {  desk ->
                        val userListBook = mutableStateOf<List<UserBook>>(listOf())
                        desk.books?.map {  book ->
                            val urlImage = IMAGE_URL + book.image
                            val urlFile = BOOK_URL + book.file
                            val userBook = UserBook(
                                name = book.name,
                                author = book.author,
                                image = urlImage,
                                file = urlFile,
                                bookId = book.id
                            )
                            topBook = userBook
                            userListBook.value += userBook
                        }
                        books.value = userListBook.value
                        booksSave.value = books.value
                        loadUserBook.value = false
                    }
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
            Log.d("Юзер из сервера", "$userOnServer")
            Log.d("КНИГИ ПОЛЬЗОВАТЕЛЯ", "${books.value}")
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
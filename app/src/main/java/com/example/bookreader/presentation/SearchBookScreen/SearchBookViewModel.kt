package com.example.bookreader.presentation.SearchBookScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Constants.IMAGE_URL
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.domain.models.BookList
import com.example.bookreader.domain.repository.BookRepository
import com.example.bookreader.domain.repository.DeskOfBookRepository
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.presentation.FilterScreen.Filter
import com.example.bookreader.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel @Inject constructor(
    private val repository: BookRepository,
    private val repositoryUser: UserInfoRepository,
    private val repositoryDesk: DeskOfBookRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    //Получение книг с сервера
    val bookList = mutableStateOf<List<BookList>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    val currentBookList = mutableStateOf<List<BookList>>(listOf())

    //Сохраняем полученные книги в кеш
    private var cachedBookList = listOf<BookList>()

    //Выбран ли жанр книги или нет
    var filterClickCheck = mutableStateOf("")

    //Для поиска книг по автору и по названию
    var searchText = mutableStateOf("")
        private set
    var isSearching = mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var user: UserInfo? = null

    var filter = Filter(
        bookName = "",
        bookAuthor = "",
        bookRating = listOf()
    )

    init {
        loadBookList()
    }

    fun onEvent(event: SearchBookEvent) {
        when (event) {
            is SearchBookEvent.OnBookClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
                searchText.value = ""
                isSearching.value = false
                Log.d("Route", "${event.route}")
                filterClickCheck.value = ""
            }

            is SearchBookEvent.OnShowFilterScreen -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is SearchBookEvent.OnTextSearchChange -> {
                isSearching.value = true
                searchText.value = event.text
                bookList.value = cachedBookList.filter { book ->
                    book.name.lowercase().startsWith(searchText.value.lowercase()) ||
                    book.author.lowercase().startsWith(searchText.value.lowercase())
                }
                Log.d("текущий поиск", "${bookList.value}")
            }

            SearchBookEvent.OnClickDeleteSearchText -> {
                bookList.value = cachedBookList
                searchText.value = ""
                isSearching.value = false
            }

            is SearchBookEvent.OnFilterGenre -> {

                if (filterClickCheck.value == event.filter) {
                    filterClickCheck.value = ""
                    filterList.replaceAll {
                        it.copy(
                            onTurn = false
                        )
                    }
                    bookList.value = cachedBookList
                } else {
                    filterClickCheck.value = event.filter
                    filterList.replaceAll {
                        it.copy(
                            onTurn = it.text == event.filter
                        )
                    }
                    Log.d("Turn filter", event.filter.lowercase())
                    filterList.sortByDescending { it.onTurn }
                    bookList.value =
                        cachedBookList.filter { it.genre.lowercase() == event.filter.lowercase() }
                    Log.d("Filter List", "${bookList.value}")
                }

            }

            is SearchBookEvent.OnChangeFilter -> {
                filterBookList(event.filter)
            }

            SearchBookEvent.OnLoad -> {
                loadUser()
            }

            SearchBookEvent.OnLoadBookList -> {
                Log.d("Список пришел еще", "ДА")
                loadBookList()
            }

            is SearchBookEvent.OnClickAddBook -> {
            if (user == null) {
                sendUiEvent(UiEvent.ShowSnackBar("Для начала войдите в аккаунт!"))
            } else {
                viewModelScope.launch {
                    repositoryDesk.addBookToDeskById(
                        userId = user?.userId!!,
                        bookId = event.bookId
                    )
                }
            }
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
            Log.d("Обновил поиск", "$user")

        }

    }

    private fun filterBookList(filterSettings: Filter) {
        if (filterSettings.bookName == "" && filterSettings.bookAuthor == "" && filterSettings.bookRating?.isEmpty() == true) {
            Log.d("ПУСТО", "МЫ ТУТ")
            bookList.value = cachedBookList
        } else {
            Log.d("НЕ ПУСТО", "ТУТ")
            bookList.value = cachedBookList
            if (filterSettings.bookName != "") {
                bookList.value = bookList.value.filter {
                    it.name.lowercase().contains(filterSettings.bookName!!.lowercase())
                }
            }
            if (filterSettings.bookAuthor != "") {
                bookList.value = bookList.value.filter {
//                    it.author.lowercase() == filterSettings.bookAuthor?.lowercase()
                    it.author.lowercase().contains(filterSettings.bookAuthor!!.lowercase())
                }
            }
            if (filterSettings.bookRating?.isEmpty() == false) {
                bookList.value = bookList.value.filter {
                    filterSettings.bookRating.contains(it.rate)
                }
            }
        }

    }

    private fun loadBookList() {
        isLoading.value = true
        loadError.value = ""
        viewModelScope.launch {
            val result = repository.getBookList()
            when (result) {
                is Resource.Success -> {
                    val bookEntries = result.data!!.mapIndexed { index, book ->
                        val url = IMAGE_URL + book.image
                        BookList(
                            name = book.name,
                            author = book.author,
                            rate = book.rate,
                            image = url,
                            genre = book.genre,
                            id = book.id
                        )
                    }
                    loadError.value = ""
                    isLoading.value = false
                    currentBookList.value += bookEntries
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
            bookList.value = currentBookList.value
            currentBookList.value = listOf()
            cachedBookList = bookList.value
            Log.d("КЕШ", "$cachedBookList")
        }

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    var filterList = mutableStateListOf(
        FilterGenre(
            "ПЬЕСА",
            false
        ),
        FilterGenre(
            "МИСТИКА",
            false
        ),
        FilterGenre(
            "ПРИКЛЮЧЕНИЯ",
            false
        ),
        FilterGenre(
            "СКАЗКА",
            false
        ),
        FilterGenre(
            "РОМАН",
            false
        ),
        FilterGenre(
            "КЛАССИКА",
            false
        ),
        FilterGenre(
            "ПОВЕСТЬ",
            false
        ),
        FilterGenre(
            "ДРАМА",
            false
        ),
    )

    var filterListSave = mutableStateListOf(
        FilterGenre(
            "ПЬЕСА",
            false
        ),
        FilterGenre(
            "МИСТИКА",
            false
        ),
        FilterGenre(
            "ПРИКЛЮЧЕНИЯ",
            false
        ),
        FilterGenre(
            "СКАЗКА",
            false
        ),
        FilterGenre(
            "РОМАН",
            false
        ),
        FilterGenre(
            "КЛАССИКА",
            false
        ),
        FilterGenre(
            "ПОВЕСТЬ",
            false
        ),
        FilterGenre(
            "ДРАМА",
            false
        ),
    )

}

data class FilterGenre(
    val text: String,
    val onTurn: Boolean
)
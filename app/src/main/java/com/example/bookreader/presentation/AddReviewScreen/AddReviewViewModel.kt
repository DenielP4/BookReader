package com.example.bookreader.presentation.AddReviewScreen

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.data.remote.responses.Review
import com.example.bookreader.domain.repository.ReviewRepository
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.presentation.UserBookScreen.UserBookEvent
import com.example.bookreader.presentation.utils.UiEvent
import com.example.bookreader.presentation.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val repository: UserInfoRepository,
    private val repositoryReview: ReviewRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var user: UserInfo? = null

    val ratingChange = mutableFloatStateOf(0f)
    val reviewTextChange = mutableStateOf("")
    val checkRatingNotNull = mutableStateOf(false)

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    var bookId: Int = -1

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        bookId = savedStateHandle.get<String>("bookId")?.toInt()!!
        Log.d("На экране отзыва", "$bookId")
    }

    fun onEvent(event: AddReviewEvent) {
        when(event) {
            AddReviewEvent.OnLoad -> {
                loadUser()
            }
            AddReviewEvent.OnClickAddReview -> {
                if (checkRatingNotNull.value) {
                    viewModelScope.launch {
                        repositoryReview.addReview(
                            id = bookId,
                            Review(
                                reviewText = reviewTextChange.value,
                                rating = ratingChange.floatValue.toInt(),
                                reviewDate = getCurrentTime(),
                                userId = user?.userId!!,
                                userName = user?.name!!
                            )
                        )
                            sendUiEvent(UiEvent.PopBackStack)
                    }
                }
            }
            is AddReviewEvent.OnReviewChangeRating -> {
                if (event.rating == 0f){
                    checkRatingNotNull.value = false
                    ratingChange.floatValue = event.rating
                } else{
                    checkRatingNotNull.value = true
                    ratingChange.floatValue = event.rating
                }
                Log.d("Мой рейтинг", "${ratingChange.floatValue}")
                Log.d("Мой чекер рейтинга", "${checkRatingNotNull.value}")
            }
            is AddReviewEvent.OnReviewTextChange -> {
                reviewTextChange.value = event.text
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
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                else -> {}
            }
            Log.d("Обновил отзыв", "$user")

        }

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
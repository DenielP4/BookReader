package com.example.bookreader.presentation.AddReviewScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.presentation.UserBookScreen.UserBookEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddReviewViewModel @Inject constructor(
    private val repository: UserInfoRepository
): ViewModel() {

    var user: UserInfo? = null

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun onEvent(event: AddReviewEvent) {
        when(event) {
            AddReviewEvent.OnLoad -> {
                loadUser()
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
}
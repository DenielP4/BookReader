package com.example.bookreader.presentation.UserBookScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserBookViewModel @Inject constructor(
    private val repository: UserInfoRepository
): ViewModel() {

    var user: UserInfo? = null

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: UserBookEvent) {
        when(event) {
            UserBookEvent.OnLoad -> {
                loadUser()
            }

            is UserBookEvent.OnClickAuth -> {
                sendUiEvent(UiEvent.Navigate(event.route))
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
            Log.d("Обновил полка", "$user")
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
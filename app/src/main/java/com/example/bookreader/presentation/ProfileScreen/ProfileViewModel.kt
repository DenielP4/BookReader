package com.example.bookreader.presentation.ProfileScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.common.Constants
import com.example.bookreader.common.Resource
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.domain.models.BookInfo
import com.example.bookreader.domain.models.BookList
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserInfoRepository
) : ViewModel() {

    var user: UserInfo? = null

    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.OnClickLogOut -> {
                viewModelScope.launch {
                    user?.let { repository.deleteUser(user!!) }
                }
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            is ProfileEvent.OnClickAuth -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }

            ProfileEvent.OnLoad -> {
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
        }

    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
package com.example.bookreader.presentation.RegScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.data.remote.responses.User
import com.example.bookreader.domain.models.UserReg
import com.example.bookreader.domain.repository.UserRepository
import com.example.bookreader.presentation.utils.UiEvent
import com.example.bookreader.presentation.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    var userNameText = mutableStateOf("")
        private set
    var userPasswordText = mutableStateOf("")
        private set
    var userRepeatPasswordText = mutableStateOf("")
        private set
    var visiblePassword = mutableStateOf(false)
        private set
    var visibleRepeatPassword = mutableStateOf(false)
        private set

    lateinit var response: Response<User>

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegEvent) {
        when(event) {
            RegEvent.OnUserRegistration -> {
                if (userPasswordText.value != userRepeatPasswordText.value){
                    sendUiEvent(UiEvent.ShowSnackBar("Пароли должны совпадать!"))
                }
                viewModelScope.launch {
                    response = repository.registration(
                        UserReg(
                            name = userNameText.value,
                            registerDate = getCurrentTime(),
                            password = userPasswordText.value
                        )
                    )
                    if (response.code() == 409){
                        sendUiEvent(UiEvent.ShowSnackBar("Пользователь с таким логином уже найден!"))
                    } else {
                        sendUiEvent(UiEvent.PopBackStack)
                    }

                }
            }
            is RegEvent.OnNameTextChange -> {
                userNameText.value = event.name
            }
            is RegEvent.OnPasswordTextChange -> {
                userPasswordText.value = event.password
            }
            is RegEvent.OnRepeatPasswordTextChange -> {
                userRepeatPasswordText.value = event.repeatPassword
            }
            RegEvent.OnDeleteNameText -> {
                userNameText.value = ""
            }
            is RegEvent.OnChangeVisiblePassword -> {
                visiblePassword.value = event.visible
            }
            is RegEvent.OnChangeVisibleRepeatPassword -> {
                visibleRepeatPassword.value = event.visibleRepeat
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
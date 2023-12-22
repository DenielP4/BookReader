package com.example.bookreader.presentation.AuthScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreader.data.locale.UserInfo
import com.example.bookreader.data.remote.responses.User
import com.example.bookreader.domain.models.UserAuth
import com.example.bookreader.domain.models.UserReg
import com.example.bookreader.domain.repository.UserInfoRepository
import com.example.bookreader.domain.repository.UserRepository
import com.example.bookreader.presentation.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository,
    private val repositoryDb: UserInfoRepository
): ViewModel() {


    var userNameText = mutableStateOf("")
        private set
    var userPasswordText = mutableStateOf("")
        private set
    var visiblePassword = mutableStateOf(false)
        private set

    lateinit var response: Response<User>

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AuthEvent) {
        when(event) {
            is AuthEvent.OnUserLogin -> {
                viewModelScope.launch {
                    response = repository.login(
                        UserAuth(
                            name = userNameText.value,
                            password = userPasswordText.value
                        )
                    )
                    Log.d("ответ после авторизации", "$response")
                    if (response.code() == 403) {
                        sendUiEvent(UiEvent.ShowSnackBar("Пользователь не найден!"))
                    } else {
                        Log.d("Пользователь", "${response.body()}")
                        repositoryDb.saveUser(
                            UserInfo(
                                id = null,
                                name = response.body()?.name,
                                registerDate = response.body()?.registerDate,
                                shelf = response.body()?.shelf,
                                userId = response.body()?.id
                            )
                        )
                        sendUiEvent(UiEvent.PopBackStack)
//                        sendUiEvent(UiEvent.Navigate(event.route))
                    }
                }

            }
            is AuthEvent.OnNameTextChange -> {
                userNameText.value = event.name
            }
            is AuthEvent.OnPasswordTextChange -> {
                userPasswordText.value = event.password
            }
            AuthEvent.OnDeleteNameText -> {
                userNameText.value = ""
            }
            is AuthEvent.OnChangeVisiblePassword -> {
                visiblePassword.value = event.visible
            }
            is AuthEvent.OnNavigateToRegistration -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }
        }
    }


    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
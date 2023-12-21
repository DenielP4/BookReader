package com.example.bookreader.presentation.RegScreen

sealed class RegEvent{
    object OnUserRegistration: RegEvent()
    data class OnNameTextChange(val name: String): RegEvent()
    data class OnPasswordTextChange(val password: String): RegEvent()
    data class OnRepeatPasswordTextChange(val repeatPassword: String): RegEvent()
    data class OnChangeVisiblePassword(val visible: Boolean): RegEvent()
    data class OnChangeVisibleRepeatPassword(val visibleRepeat: Boolean): RegEvent()
    object OnDeleteNameText: RegEvent()
}

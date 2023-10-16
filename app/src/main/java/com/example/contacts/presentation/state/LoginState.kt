package com.example.contacts.presentation.state

import com.example.contacts.domain.model.LoginAuth


sealed class LoginState {

    object Loading : LoginState()

    object Empty : LoginState()

    data class Error(val throwable: Throwable) : LoginState()

    data class Success(val data: LoginAuth) : LoginState()
}
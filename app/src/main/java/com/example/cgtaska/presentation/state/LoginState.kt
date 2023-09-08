package com.example.cgtaska.presentation.state

import com.example.cgtaska.domain.model.LoginAuth


sealed class LoginState {

    object Loading : LoginState()

    object Empty : LoginState()

    data class Error(val throwable: Throwable) : LoginState()

    data class Success(val data: LoginAuth) : LoginState()
}
package com.example.cgtaska.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cgtaska.common.Constants
import com.example.cgtaska.domain.model.LoginAuth
import com.example.cgtaska.domain.usecase.UseCases
import com.example.cgtaska.presentation.state.LoginState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val useCases: UseCases,
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState = _loginState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list: List<LoginAuth> = listOf(
                LoginAuth(
                    Constants.USER_NAME,
                    Constants.PASSWORD
                )
            )
            useCases.loginContactsInsertUseCase(list)
        }
    }


    suspend fun login(name: String, password: String) {

        _loginState.update {
            LoginState.Loading
        }

        useCases.getLoginUseCase(name, password).onEach { login ->
            _loginState.update {
                LoginState.Success(login)
            }
        }.catch { throwable: Throwable ->
            _loginState.update {
                LoginState.Error(throwable)
            }
        }.launchIn(viewModelScope)

    }


}
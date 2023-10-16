package com.example.contacts.domain.usecase.loginContact

import com.example.contacts.data.repository.Repository
import com.example.contacts.domain.model.LoginAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getLoginUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String, password: String) : Flow<LoginAuth> {
        return repository.login(name, password)
    }

}
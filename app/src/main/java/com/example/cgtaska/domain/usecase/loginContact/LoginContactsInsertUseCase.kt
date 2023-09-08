package com.example.cgtaska.domain.usecase.loginContact

import com.example.cgtaska.data.repository.Repository
import com.example.cgtaska.domain.model.LoginAuth
import javax.inject.Inject

class LoginContactsInsertUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(login: List<LoginAuth>) {
        return repository.loginInsertContact(login)
    }
}
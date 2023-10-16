package com.example.contacts.domain.usecase.loginContact

import com.example.contacts.data.repository.Repository
import com.example.contacts.domain.model.LoginAuth
import javax.inject.Inject

class LoginContactsInsertUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(login: List<LoginAuth>) {
        return repository.loginInsertContact(login)
    }
}
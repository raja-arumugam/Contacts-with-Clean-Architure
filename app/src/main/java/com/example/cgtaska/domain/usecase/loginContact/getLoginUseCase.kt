package com.example.cgtaska.domain.usecase.loginContact

import com.example.cgtaska.data.repository.Repository
import com.example.cgtaska.domain.model.LoginAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getLoginUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(name: String, password: String) : Flow<LoginAuth> {
        return repository.login(name, password)

    }

}
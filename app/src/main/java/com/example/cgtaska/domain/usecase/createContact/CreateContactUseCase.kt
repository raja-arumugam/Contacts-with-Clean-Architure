package com.example.cgtaska.domain.usecase.createContact

import com.example.cgtaska.data.repository.Repository
import com.example.cgtaska.domain.model.ContactList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateContactUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(list: List<ContactList>) {
       return repository.createUser(list)
    }

}
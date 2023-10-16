package com.example.contacts.domain.usecase.createContact

import com.example.contacts.data.repository.Repository
import com.example.contacts.domain.model.ContactList
import javax.inject.Inject

class CreateContactUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(list: List<ContactList>) {
       return repository.createUser(list)
    }

}
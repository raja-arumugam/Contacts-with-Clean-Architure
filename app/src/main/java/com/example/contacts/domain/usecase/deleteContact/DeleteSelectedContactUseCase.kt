package com.example.contacts.domain.usecase.deleteContact

import com.example.contacts.data.repository.Repository
import com.example.contacts.domain.model.ContactList
import javax.inject.Inject

class DeleteSelectedContactUseCase @Inject constructor(private val repository: Repository)  {

    suspend operator fun invoke(contact: ContactList) {
        return repository.deleteSelectedContact(contact)
    }
}
package com.example.contacts.domain.usecase.updateContact

import com.example.contacts.data.repository.Repository
import com.example.contacts.domain.model.ContactList
import javax.inject.Inject

class updateContactUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(contactList: List<ContactList>) {
        return repository.updateContact(contactList)
    }

}
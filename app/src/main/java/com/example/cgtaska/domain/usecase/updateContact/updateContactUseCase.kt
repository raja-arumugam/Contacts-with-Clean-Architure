package com.example.cgtaska.domain.usecase.updateContact

import com.example.cgtaska.data.repository.Repository
import com.example.cgtaska.domain.model.ContactList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class updateContactUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(contactList: List<ContactList>) {
        return repository.updateContact(contactList)
    }

}
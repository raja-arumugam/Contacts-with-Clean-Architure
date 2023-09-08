package com.example.cgtaska.domain.usecase.getContacts

import com.example.cgtaska.data.repository.Repository
import com.example.cgtaska.domain.model.ContactList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getSelectedContactUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(contactID: Int): Flow<ContactList> {
        return repository.getSelectedContact(contactID)
    }

}
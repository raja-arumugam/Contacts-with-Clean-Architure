package com.example.cgtaska.domain.usecase.deleteContact

import com.example.cgtaska.data.repository.Repository
import com.example.cgtaska.domain.model.ContactList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSelectedContactUseCase @Inject constructor(private val repository: Repository)  {

    suspend operator fun invoke(contact: ContactList) {
        return repository.deleteSelectedContact(contact)
    }
}
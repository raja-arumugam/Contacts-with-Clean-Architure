package com.example.contacts.domain.usecase.getContacts

import androidx.paging.PagingData
import com.example.contacts.data.repository.Repository
import com.example.contacts.domain.model.ContactList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getContactsUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(): Flow<PagingData<ContactList>> =
        repository.getAllContacts()

}
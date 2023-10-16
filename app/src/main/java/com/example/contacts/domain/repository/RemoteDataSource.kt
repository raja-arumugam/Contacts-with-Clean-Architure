package com.example.contacts.domain.repository

import androidx.paging.PagingData
import com.example.contacts.domain.model.ContactList
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllContacts(): Flow<PagingData<ContactList>>
}
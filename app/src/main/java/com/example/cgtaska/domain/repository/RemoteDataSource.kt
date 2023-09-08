package com.example.cgtaska.domain.repository

import androidx.paging.PagingData
import com.example.cgtaska.domain.model.ContactList
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllContacts(): Flow<PagingData<ContactList>>
}
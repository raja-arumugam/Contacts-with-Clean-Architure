package com.example.contacts.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.contacts.data.local.db.ContactsDatabase
import com.example.contacts.data.pagingsource.ContactsRemoteMediator
import com.example.contacts.data.remote.api.APIService
import com.example.contacts.domain.model.ContactList
import com.example.contacts.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val apiService: APIService,
    private val contactsDatabase: ContactsDatabase,
) : RemoteDataSource {

    private val contactsDao = contactsDatabase.contactDao()

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllContacts(): Flow<PagingData<ContactList>> {

        val pagingSourceFactory = { contactsDao.getAllContacts() }

        return Pager(
            config = PagingConfig(2),
            remoteMediator = ContactsRemoteMediator(
                apiService, contactsDatabase
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

}
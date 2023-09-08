package com.example.cgtaska.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cgtaska.data.local.db.ContactsDatabase
import com.example.cgtaska.data.pagingsource.ContactsRemoteMediator
import com.example.cgtaska.data.remote.api.APIService
import com.example.cgtaska.domain.model.ContactList
import com.example.cgtaska.domain.repository.RemoteDataSource
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
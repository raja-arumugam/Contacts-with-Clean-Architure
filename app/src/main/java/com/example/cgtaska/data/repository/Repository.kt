package com.example.cgtaska.data.repository

import androidx.paging.PagingData
import com.example.cgtaska.domain.model.ContactList
import com.example.cgtaska.domain.model.LoginAuth
import com.example.cgtaska.domain.repository.LocalDataSource
import com.example.cgtaska.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource,
) {

    fun getAllContacts(): Flow<PagingData<ContactList>> = remote.getAllContacts()

    suspend fun getSelectedContact(contactID: Int): Flow<ContactList> =
        local.getSelectedContact(contactID)

    suspend fun createUser(list: List<ContactList>) = local.createUser(list)

    suspend fun deleteSelectedContact(contact: ContactList) = local.deleteSelectedContact(contact)

    suspend fun updateContact(contact: List<ContactList>) = local.updateUser(contact)

    suspend fun loginInsertContact(loginAuth: List<LoginAuth>) = local.loginInsertContact(loginAuth)

    suspend fun login(name: String, password: String): Flow<LoginAuth> = local.login(name, password)

}
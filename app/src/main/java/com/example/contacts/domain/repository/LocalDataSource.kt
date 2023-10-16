package com.example.contacts.domain.repository

import com.example.contacts.domain.model.ContactList
import com.example.contacts.domain.model.LoginAuth
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun getSelectedContact(contactID: Int) : Flow<ContactList>

    suspend fun createUser(list: List<ContactList>)

    suspend fun deleteSelectedContact(contactID: ContactList)

    suspend fun updateUser(contactID: List<ContactList>)

    suspend fun loginInsertContact(loginAuth: List<LoginAuth>)

    suspend fun login(name: String, password: String): Flow<LoginAuth>
}
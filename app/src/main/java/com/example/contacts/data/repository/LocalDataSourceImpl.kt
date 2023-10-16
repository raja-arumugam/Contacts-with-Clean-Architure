package com.example.contacts.data.repository

import com.example.contacts.data.local.db.ContactsDatabase
import com.example.contacts.domain.model.ContactList
import com.example.contacts.domain.model.LoginAuth
import com.example.contacts.domain.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val contactsDatabase: ContactsDatabase) : LocalDataSource {

    override suspend fun getSelectedContact(contactID: Int): Flow<ContactList> {
        return contactsDatabase.contactDao().getSelectedContact(contactID)
    }

    override suspend fun createUser(list: List<ContactList>) {
        return contactsDatabase.contactDao().insertContact(list)
    }

    override suspend fun deleteSelectedContact(contactID: ContactList) {
        return contactsDatabase.contactDao().deleteSelectedContact(contactID)
    }

    override suspend fun updateUser(contact: List<ContactList>) {
        return contactsDatabase.contactDao().updateUserContact(contact)
    }

    override suspend fun loginInsertContact(loginAuth: List<LoginAuth>) {
        return contactsDatabase.contactDao().loginInsertContact(loginAuth)
    }

    override suspend fun login(name: String, password: String): Flow<LoginAuth> {
        return contactsDatabase.contactDao().login(name, password)
    }

}
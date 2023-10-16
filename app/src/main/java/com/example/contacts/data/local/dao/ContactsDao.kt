package com.example.contacts.data.local.dao

import androidx.room.Query
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.contacts.domain.model.ContactList
import com.example.contacts.domain.model.LoginAuth
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contactList ORDER BY id ASC")
    fun getAllContacts(): PagingSource<Int, ContactList>

    @Query("SELECT * FROM contactList WHERE id = :contactID")
    fun getSelectedContact(contactID : Int) : Flow<ContactList>

    @Update()
    fun updateUserContact(contact : List<ContactList>)

    @Delete()
    fun deleteSelectedContact(contact : ContactList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContacts(contactList: List<ContactList>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contactList: List<ContactList>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun loginInsertContact(loginAuth: List<LoginAuth>)

    @Query("SELECT * FROM login WHERE name LIKE :username AND password LIKE :password")
    fun login(username: String, password: String): Flow<LoginAuth>

    @Query("DELETE FROM contactList")
    suspend fun deleteAllContacts()
}
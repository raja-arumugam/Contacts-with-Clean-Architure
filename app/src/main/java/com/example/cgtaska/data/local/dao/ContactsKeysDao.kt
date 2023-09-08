package com.example.cgtaska.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cgtaska.data.local.model.ContactsRemoteKeys

@Dao
interface ContactsKeysDao {

    @Query("SELECT * FROM contact_remote_key WHERE contacts_id = :contactId")
    suspend fun getRemoteKeysById(contactId: Int): ContactsRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<ContactsRemoteKeys>)

    @Query("DELETE FROM contact_remote_key")
    suspend fun deleteAllRemoteKeys()

    @Query("Select created_at From contact_remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

}
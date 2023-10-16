package com.example.contacts.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_remote_key")
data class ContactsRemoteKeys (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "contacts_id")
    val contactsID: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
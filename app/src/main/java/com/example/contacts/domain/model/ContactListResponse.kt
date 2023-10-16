package com.example.contacts.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ContactListResponse(
    val `data`: List<ContactList>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)

@Entity(tableName = "contactList")
data class ContactList(
    @field:SerializedName("avatar")
    val avatar: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("first_name")
    val first_name: String,
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("last_name")
    val last_name: String
)
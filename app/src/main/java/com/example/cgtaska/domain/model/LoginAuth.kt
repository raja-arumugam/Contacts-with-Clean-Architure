package com.example.cgtaska.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("login")
data class LoginAuth(
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("password")
    val password: String,
) {
    @PrimaryKey(autoGenerate = false)
    @field:SerializedName("userId")
    var userId: Int = 1
}
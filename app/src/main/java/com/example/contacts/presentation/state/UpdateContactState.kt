package com.example.contacts.presentation.state

import com.example.contacts.domain.model.ContactList

sealed class UpdateContactState {

    object Loading : UpdateContactState()

    object Empty : UpdateContactState()

    data class Error(val throwable: Throwable) : UpdateContactState()

    data class Success(val data: List<ContactList>) : UpdateContactState()
}
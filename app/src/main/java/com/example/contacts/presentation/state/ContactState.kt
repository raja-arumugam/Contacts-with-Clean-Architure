package com.example.contacts.presentation.state

import com.example.contacts.domain.model.ContactList

sealed class ContactState {

    object Loading : ContactState()

    object Empty : ContactState()

    data class Error(val throwable: Throwable) : ContactState()

    data class Success(val data: ContactList) : ContactState()
}
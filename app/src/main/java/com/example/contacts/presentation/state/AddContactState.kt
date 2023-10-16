package com.example.contacts.presentation.state

import com.example.contacts.domain.model.ContactList

sealed class AddContactState {

    object Loading : AddContactState()

    object Empty : AddContactState()

    data class Error(val throwable: Throwable) : AddContactState()

    data class Success(val data: List<ContactList>) : AddContactState()
}
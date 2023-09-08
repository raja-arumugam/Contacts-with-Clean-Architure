package com.example.cgtaska.presentation.state

import com.example.cgtaska.domain.model.ContactList

sealed class DeleteContactState {

    object Loading : DeleteContactState()

    object Empty : DeleteContactState()

    data class Error(val throwable: Throwable) : DeleteContactState()

    data class Success(val data: ContactList) : DeleteContactState()
}
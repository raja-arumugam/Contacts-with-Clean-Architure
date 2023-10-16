package com.example.contacts.domain.usecase

import com.example.contacts.domain.usecase.createContact.CreateContactUseCase
import com.example.contacts.domain.usecase.deleteContact.DeleteSelectedContactUseCase
import com.example.contacts.domain.usecase.getContacts.getContactsUseCase
import com.example.contacts.domain.usecase.getContacts.getSelectedContactUseCase
import com.example.contacts.domain.usecase.loginContact.LoginContactsInsertUseCase
import com.example.contacts.domain.usecase.loginContact.getLoginUseCase
import com.example.contacts.domain.usecase.updateContact.updateContactUseCase

data class UseCases(
    val getContactsUseCase: getContactsUseCase,
    val getSelectedContactUseCase: getSelectedContactUseCase,
    val createContactUseCase: CreateContactUseCase,
    val deleteSelectedContactUseCase: DeleteSelectedContactUseCase,
    val updateContactUseCase:  updateContactUseCase,
    val loginContactsInsertUseCase: LoginContactsInsertUseCase,
    val getLoginUseCase: getLoginUseCase,
)
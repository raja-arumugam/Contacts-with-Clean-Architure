package com.example.cgtaska.domain.usecase

import com.example.cgtaska.domain.usecase.createContact.CreateContactUseCase
import com.example.cgtaska.domain.usecase.deleteContact.DeleteSelectedContactUseCase
import com.example.cgtaska.domain.usecase.getContacts.getContactsUseCase
import com.example.cgtaska.domain.usecase.getContacts.getSelectedContactUseCase
import com.example.cgtaska.domain.usecase.loginContact.LoginContactsInsertUseCase
import com.example.cgtaska.domain.usecase.loginContact.getLoginUseCase
import com.example.cgtaska.domain.usecase.updateContact.updateContactUseCase

data class UseCases(
    val getContactsUseCase: getContactsUseCase,
    val getSelectedContactUseCase: getSelectedContactUseCase,
    val createContactUseCase: CreateContactUseCase,
    val deleteSelectedContactUseCase: DeleteSelectedContactUseCase,
    val updateContactUseCase:  updateContactUseCase,
    val loginContactsInsertUseCase: LoginContactsInsertUseCase,
    val getLoginUseCase: getLoginUseCase,
)
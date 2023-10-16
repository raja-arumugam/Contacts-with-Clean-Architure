package com.example.contacts.di.modules

import com.example.contacts.data.repository.Repository
import com.example.contacts.domain.usecase.UseCases
import com.example.contacts.domain.usecase.createContact.CreateContactUseCase
import com.example.contacts.domain.usecase.deleteContact.DeleteSelectedContactUseCase
import com.example.contacts.domain.usecase.getContacts.getContactsUseCase
import com.example.contacts.domain.usecase.getContacts.getSelectedContactUseCase
import com.example.contacts.domain.usecase.loginContact.LoginContactsInsertUseCase
import com.example.contacts.domain.usecase.loginContact.getLoginUseCase
import com.example.contacts.domain.usecase.updateContact.updateContactUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            getContactsUseCase = getContactsUseCase(repository),
            getSelectedContactUseCase = getSelectedContactUseCase(repository),
            createContactUseCase = CreateContactUseCase(repository),
            deleteSelectedContactUseCase = DeleteSelectedContactUseCase(repository),
            updateContactUseCase = updateContactUseCase(repository),
            loginContactsInsertUseCase = LoginContactsInsertUseCase(repository),
            getLoginUseCase = getLoginUseCase(repository),
        )
    }
}
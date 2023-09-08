package com.example.cgtaska.di.modules

import com.example.cgtaska.data.repository.Repository
import com.example.cgtaska.domain.usecase.UseCases
import com.example.cgtaska.domain.usecase.createContact.CreateContactUseCase
import com.example.cgtaska.domain.usecase.deleteContact.DeleteSelectedContactUseCase
import com.example.cgtaska.domain.usecase.getContacts.getContactsUseCase
import com.example.cgtaska.domain.usecase.getContacts.getSelectedContactUseCase
import com.example.cgtaska.domain.usecase.loginContact.LoginContactsInsertUseCase
import com.example.cgtaska.domain.usecase.loginContact.getLoginUseCase
import com.example.cgtaska.domain.usecase.updateContact.updateContactUseCase
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
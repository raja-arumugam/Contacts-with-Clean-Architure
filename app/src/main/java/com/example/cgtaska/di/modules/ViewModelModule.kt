package com.example.cgtaska.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cgtaska.di.component.viewmodel.ViewModelFactory
import com.example.cgtaska.di.component.viewmodel.ViewModelKey
import com.example.cgtaska.presentation.viewmodel.AddContactViewModel
import com.example.cgtaska.presentation.viewmodel.ContactsDetailsViewModel
import com.example.cgtaska.presentation.viewmodel.ContactsViewModel
import com.example.cgtaska.presentation.viewmodel.LoginViewModel
import com.example.cgtaska.presentation.viewmodel.UpdateContactViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindContactsViewModel(viewModel: ContactsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsDetailsViewModel::class)
    abstract fun bindContactsDetailsViewModel(viewModel: ContactsDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddContactViewModel::class)
    abstract fun bindAddContactViewModel(viewModel: AddContactViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateContactViewModel::class)
    abstract fun bindUpdateContactViewModel(viewModel: UpdateContactViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel



    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
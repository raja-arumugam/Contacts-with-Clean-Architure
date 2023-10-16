package com.example.contacts.di.modules

import com.example.contacts.presentation.fragement.UpdateContactFragment
import com.example.contacts.presentation.fragement.AddContactFragment
import com.example.contacts.presentation.ui.fragement.SplashFragment
import com.example.contacts.presentation.ui.fragement.ContactsDetailsFragment
import com.example.contacts.presentation.fragement.ContactsListFragment
import com.example.contacts.presentation.ui.fragement.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeContactsListFragment(): ContactsListFragment

    @ContributesAndroidInjector
    abstract fun contributeContactsDetailsFragment(): ContactsDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddContactFragment(): AddContactFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateContactFragment(): UpdateContactFragment

    @ContributesAndroidInjector
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment
}
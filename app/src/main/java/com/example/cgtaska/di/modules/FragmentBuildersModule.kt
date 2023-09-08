package com.example.cgtaska.di.modules

import com.example.cgtaska.presentation.fragement.UpdateContactFragment
import com.example.cgtaska.presentation.fragement.AddContactFragment
import com.example.cgtaska.presentation.ui.fragement.SplashFragment
import com.example.cgtaska.presentation.ui.fragement.ContactsDetailsFragment
import com.example.cgtaska.presentation.fragement.ContactsListFragment
import com.example.cgtaska.presentation.ui.fragement.LoginFragment
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
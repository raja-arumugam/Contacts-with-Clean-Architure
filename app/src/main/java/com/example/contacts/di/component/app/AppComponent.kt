package com.example.contacts.di.component.app

import android.app.Application
import com.example.contacts.App
import com.example.contacts.di.modules.DatabaseModule
import com.example.contacts.di.modules.MainActivityModule
import com.example.contacts.di.modules.NetworkModule
import com.example.contacts.di.modules.RepositoryModule
import com.example.contacts.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
        MainActivityModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
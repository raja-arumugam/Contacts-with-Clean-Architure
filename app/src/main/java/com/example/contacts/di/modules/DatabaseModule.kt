package com.example.contacts.di.modules

import android.app.Application
import com.example.contacts.data.local.db.ContactsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideContactsDb(app: Application) = ContactsDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideContactsDao(db: ContactsDatabase) = db.contactDao()

}
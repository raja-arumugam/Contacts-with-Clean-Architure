package com.example.cgtaska.di.modules

import android.app.Application
import com.example.cgtaska.data.local.db.ContactsDatabase
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
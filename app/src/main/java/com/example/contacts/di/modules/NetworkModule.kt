package com.example.contacts.di.modules

import com.example.contacts.BuildConfig
import com.example.contacts.data.local.db.ContactsDatabase
import com.example.contacts.data.remote.api.APIService
import com.example.contacts.data.repository.LocalDataSourceImpl
import com.example.contacts.data.repository.RemoteDataSourceImpl
import com.example.contacts.domain.repository.LocalDataSource
import com.example.contacts.domain.repository.RemoteDataSource
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideAPIService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, APIService::class.java)

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return provideRetrofitInstance(okhttpClient, converterFactory).create(clazz)
    }

    private fun provideRetrofitInstance(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        apiService : APIService,
        contactsDatabase: ContactsDatabase,
    ) : RemoteDataSource {
        return RemoteDataSourceImpl(
            apiService = apiService,
            contactsDatabase = contactsDatabase,
        )
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(database: ContactsDatabase) : LocalDataSource {
        return LocalDataSourceImpl(database)
    }
}
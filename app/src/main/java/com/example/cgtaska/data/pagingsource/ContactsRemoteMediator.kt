package com.example.cgtaska.data.pagingsource

import android.annotation.SuppressLint
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cgtaska.data.local.db.ContactsDatabase
import com.example.cgtaska.data.local.model.ContactsRemoteKeys
import com.example.cgtaska.data.remote.api.APIService
import com.example.cgtaska.domain.model.ContactList
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ContactsRemoteMediator @Inject constructor(
    private val apiService: APIService,
    private val contactsDatabase: ContactsDatabase
) : RemoteMediator<Int, ContactList>() {

    override suspend fun initialize(): InitializeAction {

        val cacheTimeout = TimeUnit.MICROSECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (contactsDatabase.contactRemoteKeysDao()
                .getCreationTime() ?: 0) < cacheTimeout
        ) {
            return InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ContactList>): ContactsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                contactsDatabase.contactRemoteKeysDao().getRemoteKeysById(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ContactList>): ContactsRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { contact ->
            contactsDatabase.contactRemoteKeysDao().getRemoteKeysById(contact.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ContactList>): ContactsRemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { contact ->
            contactsDatabase.contactRemoteKeysDao().getRemoteKeysById(contact.id)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ContactList>
    ): MediatorResult {
        val page: Int = when (loadType) {

            LoadType.REFRESH -> {
                val contactRemoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                contactRemoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val contactRemoteKeys = getRemoteKeyForLastItem(state)
                val prevKey = contactRemoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = contactRemoteKeys != null)
            }

            LoadType.APPEND -> {
                val contactsRemoteKeys = getRemoteKeyForFirstItem(state)
                val nextKey = contactsRemoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = contactsRemoteKeys != null)
            }
        }

        try {

            val apiResponse = apiService.getContactsResponse(page)
            val contactList = apiResponse.body()?.data

            val endOfPaginationReached = contactList?.isEmpty()

                contactsDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        contactsDatabase.contactDao().deleteAllContacts()
                        contactsDatabase.contactRemoteKeysDao().deleteAllRemoteKeys()
                    }

                    val prevKey = if (page > 1) page - 1 else null
                    val nextKey = if (endOfPaginationReached!!) null else page + 1

                    val remoteKeys = contactList.map {
                        ContactsRemoteKeys(
                            contactsID = it.id,
                            prevKey = prevKey,
                            currentPage = page,
                            nextKey = nextKey
                        )
                    }

                    contactsDatabase.contactDao().insertAllContacts(contactList)

                    contactsDatabase.contactRemoteKeysDao()
                        .insertRemoteKeys(remoteKeys = remoteKeys)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)

        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }

    }


}
package com.example.cgtaska.data.remote.api

import com.example.cgtaska.domain.model.ContactListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("users")
    suspend fun getContactsResponse(
        @Query("page")
        pageNo: Int
    ) :Response<ContactListResponse>

}
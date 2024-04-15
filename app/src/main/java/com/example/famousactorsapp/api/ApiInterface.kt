package com.example.famousactorsapp.api

import com.example.famousactorsapp.models.FamousActorsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {
    @GET("celebrity")
    fun getActors(
        @Header("x-api-key") apiKey: String,
        @Query("name") name: String
    ) : Call<List<FamousActorsItem>>
}
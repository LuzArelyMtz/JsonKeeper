package com.example.jsonkeeper.api

import com.example.jsonkeeper.api.model.JsonKeeperResponse
import retrofit2.Call
import retrofit2.http.GET

interface IJsonKeeperAPIClient {
    @GET("/b/WN0G")
    fun getResponse(): Call<JsonKeeperResponse>
}
package com.example.jsonkeeper.api

import com.example.jsonkeeper.api.model.JsonKeeperResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IJsonKeeperAPIClient {
    @GET("/b/WN0G")
    fun getResponse(): Single<JsonKeeperResponse>
}
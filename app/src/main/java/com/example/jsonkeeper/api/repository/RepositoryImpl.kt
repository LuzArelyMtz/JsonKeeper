package com.example.jsonkeeper.api.repository

import com.example.jsonkeeper.api.IJsonKeeperAPIClient
import com.example.jsonkeeper.api.model.JsonKeeperItem
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val JsonKeeperAPI: IJsonKeeperAPIClient) :
    IRepository {
    override suspend fun getJsonKeeperList(): List<JsonKeeperItem> {
        return JsonKeeperAPI.getResponse().items
    }
}
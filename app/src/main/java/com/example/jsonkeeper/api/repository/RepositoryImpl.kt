package com.example.jsonkeeper.api.repository

import com.example.jsonkeeper.api.IJsonKeeperAPIClient
import com.example.jsonkeeper.api.model.JsonKeeperItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val JsonKeeperAPI: IJsonKeeperAPIClient) :
    IRepository {
    override suspend fun getJsonKeeperList(): Flow<Result<List<JsonKeeperItem>>> {
        val list = JsonKeeperAPI.getResponse().items
        return flow {
            emit(Result.success(list))
        }
    }
}
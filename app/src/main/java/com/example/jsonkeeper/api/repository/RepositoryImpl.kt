package com.example.jsonkeeper.api.repository

import com.example.jsonkeeper.api.JsonKeeperAPIImpl
import com.example.jsonkeeper.api.model.JsonKeeperItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(private val JsonKeeperAPI: JsonKeeperAPIImpl) : IRepository {
    override suspend fun getJsonKeeperList(): Flow<Result<List<JsonKeeperItem>>> {
        val list = JsonKeeperAPI.getResponse().items
        return flow {
            emit(Result.success(list))
        }
    }
}
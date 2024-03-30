package com.example.jsonkeeper.api.repository

import com.example.jsonkeeper.api.model.JsonKeeperItem
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getJsonKeeperList(): Flow<Result<List<JsonKeeperItem>>>
}
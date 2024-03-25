package com.example.jsonkeeper.api.repository

import com.example.jsonkeeper.api.model.JsonKeeperItem

interface IRepository {
    suspend fun getJsonKeeperList(): List<JsonKeeperItem>
}
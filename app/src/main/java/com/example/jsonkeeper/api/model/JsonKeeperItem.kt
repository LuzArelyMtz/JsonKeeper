package com.example.jsonkeeper.api.model

import com.google.gson.annotations.SerializedName

data class JsonKeeperItem(
    val date: String,
    val description: String,
    val img: String,
    @SerializedName("title", alternate = ["Title"])
    val title: String
)
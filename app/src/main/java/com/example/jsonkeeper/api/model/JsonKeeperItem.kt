package com.example.jsonkeeper.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JsonKeeperItem(
    val date: String,
    val description: String,
    val img: String,
    @SerializedName("title", alternate = ["Title"])
    val title: String
) : Parcelable
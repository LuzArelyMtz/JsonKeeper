package com.example.jsonkeeper.ui.adapter

import android.view.View
import com.example.jsonkeeper.api.model.JsonKeeperItem

interface OnItemClickListener {
    fun onClick(v: View?, jsonKeeperItem: JsonKeeperItem)
}
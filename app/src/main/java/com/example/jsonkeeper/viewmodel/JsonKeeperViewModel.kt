package com.example.jsonkeeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonkeeper.api.JsonKeeperAPIImpl
import com.example.jsonkeeper.api.model.JsonKeeperItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JsonKeeperViewModel : ViewModel() {

    private var _livedataResponse = MutableLiveData<List<JsonKeeperItem>>()
    var livedataResponse: LiveData<List<JsonKeeperItem>> = _livedataResponse
    private var _livedataJsonKeeperItem = MutableLiveData<JsonKeeperItem>()
    var livedataJsonKeeperItem: LiveData<JsonKeeperItem> = _livedataJsonKeeperItem

    fun getJsonKeeper() {
        viewModelScope.launch(Dispatchers.IO) {
            _livedataResponse.postValue(JsonKeeperAPIImpl().getResponse().items)
        }
    }

    fun setJsonKeeperItem(data: JsonKeeperItem) {
        _livedataJsonKeeperItem.value = data
    }
}
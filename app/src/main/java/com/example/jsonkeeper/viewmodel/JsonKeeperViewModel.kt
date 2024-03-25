package com.example.jsonkeeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jsonkeeper.api.model.JsonKeeperItem
import com.example.jsonkeeper.api.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class JsonKeeperViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    private var _livedataResponse = MutableLiveData<List<JsonKeeperItem>>()
    var livedataResponse: LiveData<List<JsonKeeperItem>> = _livedataResponse

    fun getJsonKeeper() {
        viewModelScope.launch(Dispatchers.IO) {
            _livedataResponse.postValue(repository.getJsonKeeperList())
        }
    }
}
package com.example.jsonkeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.jsonkeeper.api.JsonKeeperAPIImpl
import com.example.jsonkeeper.api.model.JsonKeeperItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var tvJsonResponse: TextView

    private var _livedataResponse = MutableLiveData<List<JsonKeeperItem>>()
    var livedataResponse: LiveData<List<JsonKeeperItem>> = _livedataResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvJsonResponse = findViewById(R.id.tvJsonResponse)

        GlobalScope.launch {
            _livedataResponse.postValue(JsonKeeperAPIImpl().getResponse().items)
        }

        livedataResponse.observe(this, Observer { jsonKeeperList ->
            tvJsonResponse.text = jsonKeeperList[0].toString()
        })
    }
}
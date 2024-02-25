package com.example.jsonkeeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jsonkeeper.viewmodel.JsonKeeperViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var tvJsonResponse: TextView
    private lateinit var viewModel: JsonKeeperViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvJsonResponse = findViewById(R.id.tvJsonResponse)

        viewModel = ViewModelProvider(this)[JsonKeeperViewModel::class.java]

        viewModel.getJsonKeeper()

        viewModel.livedataResponse.observe(this, Observer { jsonKeeperList ->
            tvJsonResponse.text = jsonKeeperList[0].toString()
        })
    }
}